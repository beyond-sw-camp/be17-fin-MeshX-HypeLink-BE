package MeshX.HypeLink.head_office.message.service;

import MeshX.HypeLink.head_office.message.model.dto.request.MessageCreateReq;
import MeshX.HypeLink.head_office.message.model.dto.response.MessageInfoRes;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class MessageService {

    @Transactional
    public void createMessage(MessageCreateReq dto){
    }
}
