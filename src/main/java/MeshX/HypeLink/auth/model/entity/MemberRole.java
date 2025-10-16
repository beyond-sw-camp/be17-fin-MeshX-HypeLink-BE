package MeshX.HypeLink.auth.model.entity;

public enum MemberRole {
    ADMIN("총괄 관리자"), MANAGER("중간 관리자"), BRANCH_MANAGER("지점장"), POS_MEMBER("포스기"), DRIVER("운전기사");

    private final String description;

    MemberRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
