package MeshX.HypeLink.head_office.chat.service;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.auth.model.entity.MemberRole;
import MeshX.HypeLink.auth.repository.MemberJpaRepositoryVerify;
import MeshX.HypeLink.head_office.chat.exception.ChatException;
import MeshX.HypeLink.head_office.chat.exception.ChatExceptionMessage;
import MeshX.HypeLink.head_office.chat.model.dto.req.ChatMessageReqDto;
import MeshX.HypeLink.head_office.chat.model.dto.res.ChatMessageResDto;
import MeshX.HypeLink.head_office.chat.model.dto.res.MessageUserListResDto;
import MeshX.HypeLink.head_office.chat.model.entity.ChatMessage;
import MeshX.HypeLink.head_office.chat.repository.ChatMessageJpaRepositoryVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import MeshX.HypeLink.common.Page.PageReq;
import MeshX.HypeLink.common.Page.PageRes;
import org.springframework.data.domain.Page;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final ChatMessageJpaRepositoryVerify chatMessageRepository;
    private final MemberJpaRepositoryVerify memberRepository;
    private final SimpMessagingTemplate messagingTemplate;

    private static final Set<MemberRole> ALLOWED_ROLES = Set.of(
            MemberRole.ADMIN,
            MemberRole.MANAGER,
            MemberRole.BRANCH_MANAGER
    );

    private Member validateSenderByEmail(String email) {
        Member sender = memberRepository.findByEmail(email);
        if (sender == null) {
            throw new ChatException(ChatExceptionMessage.SENDER_NOT_FOUND);
        }
        return sender;
    }

    private Member validateReceiverById(Integer id) {
        Member receiver = memberRepository.findById(id);
        if (receiver == null) {
            throw new ChatException(ChatExceptionMessage.RECEIVER_NOT_FOUND);
        }
        return receiver;
    }

    private void validateMessageContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new ChatException(ChatExceptionMessage.INVALID_MESSAGE_CONTENT);
        }
    }

    @Transactional
    public void saveAndSendMessage(ChatMessageReqDto reqDto, String senderEmail) {
        Member sender = validateSenderByEmail(senderEmail);
        Member receiver = validateReceiverById(reqDto.getReceiverId());
        validateMessageContent(reqDto.getContent());

        ChatMessage chatMessage = ChatMessage.builder()
                .sender(sender)
                .receiver(receiver)
                .content(reqDto.getContent())
                .build();

        chatMessageRepository.save(chatMessage);

        ChatMessageResDto resDto = ChatMessageResDto.fromEntity(chatMessage);

        messagingTemplate.convertAndSendToUser(
                receiver.getEmail(), "/queue/messages",
                resDto
        );

        messagingTemplate.convertAndSendToUser(
                sender.getEmail(), "/queue/messages",
                resDto
        );
    }

    @Transactional
    public ChatMessageResDto saveMessage(ChatMessageReqDto reqDto, String senderEmail) {
        Member sender = validateSenderByEmail(senderEmail);
        Member receiver = validateReceiverById(reqDto.getReceiverId());
        validateMessageContent(reqDto.getContent());

        ChatMessage chatMessage = ChatMessage.builder()
                .sender(sender)
                .receiver(receiver)
                .content(reqDto.getContent())
                .build();

        chatMessageRepository.save(chatMessage);

        return ChatMessageResDto.fromEntity(chatMessage);
    }

    public PageRes<ChatMessageResDto> getChatHistory(String user1Email, Integer user2Id, PageReq pageReq) {
        Member user1 = validateSenderByEmail(user1Email);
        Member user2 = validateReceiverById(user2Id);

        Page<ChatMessage> conversationPage = chatMessageRepository.findConversation(user1, user2, pageReq.toPageRequest());

        Page<ChatMessageResDto> dtoPage = conversationPage.map(ChatMessageResDto::fromEntity);

        return PageRes.toDto(dtoPage);
    }

    @Transactional
    public void markMessagesAsRead(String receiverEmail, Integer senderId) {
        Member receiver = validateSenderByEmail(receiverEmail);
        Member sender = validateReceiverById(senderId);

        List<ChatMessage> unreadMessages = chatMessageRepository.findUnreadMessages(receiver, sender);

        for (ChatMessage message : unreadMessages) {
            message.markAsRead();
            chatMessageRepository.save(message);

            ChatMessageResDto resDto = ChatMessageResDto.fromEntity(message);
            messagingTemplate.convertAndSendToUser(
                    sender.getEmail(), "/queue/read-receipt",
                    resDto
            );
        }
    }

    public List<MessageUserListResDto> getChatUserList(String currentUserEmail) {
        List<Member> allMembers = memberRepository.findAll();
        Member currentUser = validateSenderByEmail(currentUserEmail);

        // 현재 유저가 받은 읽지 않은 메시지 수 조회
        List<Object[]> unreadCounts = chatMessageRepository.countUnreadMessagesByReceiverGroupBySender(currentUser);
        Map<Integer, Long> unreadCountMap = unreadCounts.stream()
                .collect(Collectors.toMap(
                        arr -> (Integer) arr[0],  // sender ID
                        arr -> (Long) arr[1]       // count
                ));

        return allMembers.stream()
                .filter(member -> ALLOWED_ROLES.contains(member.getRole()))
                .filter(member -> !member.getEmail().equals(currentUserEmail)) // 자기 자신 제외
                .map(member -> MessageUserListResDto.builder()
                        .id(member.getId())
                        .email(member.getEmail())
                        .name(member.getName())
                        .role(member.getRole())
                        .unreadCount(unreadCountMap.getOrDefault(member.getId(), 0L).intValue())
                        .build())
                .toList();
    }
}
