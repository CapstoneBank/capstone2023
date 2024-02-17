class UserDTO {
    private String userId;
    private String password;

    // Constructors
    public UserDTO() {
    	this.userId = "0";
    	this.password = "1234";
    }

    public UserDTO(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}