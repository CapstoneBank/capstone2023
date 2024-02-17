public class CardDTO {
    private String cardId;
    private String userId;
    private String AccountId;
    private String cardType;

    // Constructors
    public CardDTO() {}

    public CardDTO(String cardId, String userId, String cardType, String AccountId) {
        this.cardId = cardId;
        this.userId = userId;
        this.cardType = cardType;
        this.AccountId=AccountId;
    }
    
    

    // Getters and Setters
    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
    
    public String getAccountId() {
    	return AccountId;
    }
    
    public void setAccountId(String AccountId) {
    	this.AccountId=AccountId;
    }
    
}