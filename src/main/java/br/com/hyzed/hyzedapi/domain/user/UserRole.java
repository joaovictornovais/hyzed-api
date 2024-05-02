package br.com.hyzed.hyzedapi.domain.user;

public enum UserRole {

    ADMIN("admin"),
    USER("user");

    private String role;

    private UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }

}
