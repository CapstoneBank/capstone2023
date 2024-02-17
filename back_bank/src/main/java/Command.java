import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class Command {

//	public static void main(String[] args) {
//		Command command = new Command();
//		System.out.println(command.getAccountsByUserAndType("dksdfdfgdgdfs", "dfs"));
//		command.transferMoney("dfsss", "dfs", 300);
//		
//		
//	}
	
	public static String generateAccountNum() {
        Random random = new Random();
        return String.format("%04d%04d%04d", 
            random.nextInt(10000),
            random.nextInt(10000),
            random.nextInt(10000)
        );
    }
	
	
	public boolean login(String user_id, String password) {
		UserDAO u = new UserDAO();
		UserDTO login_user = u.getUser(user_id);
		if(login_user == null) return false;
		else if(login_user.getPassword().equals(password)) return true;
		else return false;
	}
	
	public void join(String user_id, String password) {
		UserDAO u = new UserDAO();
		u.createUser(new UserDTO(user_id, password));
	}
	
	public static String generateAccountNumber() {
	    Random random = new Random();
	    return String.format("%04d%04d", 
	        random.nextInt(10000),
	        random.nextInt(10000)
	    );
	}
	
	public void createCard(String user_id, String cardtype, String account_id) {
		
		System.out.print(account_id);
		CardDAO cardDAO = new CardDAO();
 	   	cardDAO.createCard(new CardDTO(generateAccountNum(), user_id, cardtype, account_id));
	}
	
	public List<CardDTO> viewCard(String user_id) {
		CardDAO cardDAO = new CardDAO();
		return cardDAO.getCardsByUserAndType(user_id);
	}

	//조회관련1
	public List<AccountDTO> getAccountsByUserAndType(String userId, String accountType){
		// 유저아이디와 계좌 타입을 매개변수로 받고 해당하는 계좌들을 반환한다.
		AccountDAO a = new AccountDAO();
		List<AccountDTO> a_List  = a.getAccountsByUser(userId);
		List<AccountDTO> res = new ArrayList<AccountDTO>();
		
		for(int i=0; i<a_List.size(); i++) {
			AccountDTO ac = a_List.get(i);
			if(ac.getAccountType().equals(accountType)) {
				res.add(ac);
			}
		}
		
		return res;
	}
	
	
	
	//조회관련2
	public double getAccountBalance(String accountId) {
		// 계좌번호를 입력하면 잔액을 반환한다.
		AccountDAO a = new AccountDAO();
		AccountDTO ac = a.getAccount(accountId);
		
		return ac.getBalance();
	}
	
	/*
	 3. `getTransactionHistory(accountId) -> List[TransactionDTO]`
	 */

	//조회관련3
	public List<TransactionDTO> getTransactionHistory(String accountId){
		// 계좌번호를 알면 이체기록 반환한다.
		TransactionDAO transactionDAO = new TransactionDAO();
		
	    return transactionDAO.getTransactionsByAccount(accountId);
	}
	
	
	
	
	//이체관련
	public boolean transferMoney(String fromAccountId, String toAccountId, int amount) {
		// 첫번째 매개변수로 받은 계좌번호로부터 두번째 매개변수로 받은 계좌번호로 amount만큼의 돈을 이체한다.
		AccountDAO a = new AccountDAO();
		AccountDTO fa = a.getAccount(fromAccountId);
		AccountDTO ta = a.getAccount(toAccountId);
		
		TransactionDAO transactionDAO = new TransactionDAO();
		transactionDAO.createTransaction(new TransactionDTO(generateAccountNumber(), ta.getAccountId(), fa.getAccountId(), amount, "계좌", new Date() ));
		
		// fa나 ta의 계좌가 없거나 fa의 잔액이 amount보다 적을 때 처리
		if(fa==null || ta==null) return false;
		if(fa.getBalance()<amount) return false;
		
		// amount만큼 fa계좌에서 빼고 ta계좌에 추가
		double numa = fa.getBalance();
		numa -= amount;
		fa.setBalance(numa);
		
		double numb = ta.getBalance();
		numb += amount;
		ta.setBalance(numb);
		
		
		// 계좌 잔액 최신화
		a.updateAccount(fa);
		a.updateAccount(ta);
		
		return true;
	}
	
	//입금기능
	public boolean depositAccount(String toAccountId, int amount) {
		// 첫번째 매개변수로 받은 계좌번호로부터 두번째 매개변수로 받은 계좌번호로 amount만큼의 돈을 이체한다.
		AccountDAO a = new AccountDAO();
		AccountDTO ta = a.getAccount(toAccountId);
		
		TransactionDAO transactionDAO = new TransactionDAO();
		transactionDAO.createTransaction(new TransactionDTO(generateAccountNumber(), ta.getAccountId(), ta.getAccountId(), amount, "계좌", new Date() ));
		
		double numb = ta.getBalance();
		numb += amount;
		ta.setBalance(numb);
		
		a.updateAccount(ta);
		
		return true;
	}
	
	
	
	
	
	
	/*
	 (신청) applyDepositAccounts(money, userid) -> boolean
	(통장깨기)transDepositAccounts(account_id, DepositAccount_id) -> boolean
	(예금통장 목록보기)viewDepositAccounts(user_id) -> List<DepositAccountsDTO> 
	 */
	
	
	//예금신청은 다음과 같음
	public boolean applyDepositAccounts(String userid) {
		// 예금통장 신청(예금 금액, 예금계좌번호)
		DepositAccountDAO a = new DepositAccountDAO();
		
		//현재 날짜에서 1년 추가
		Date currentDate = new Date();
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.YEAR, 1);
        
        Date oneYearLater = calendar.getTime();
        
        //통장 정보는 공유함
        String number = generateAccountNum();
        //account에 정보를 저장함으로서, 이체를 받을 수 있도록 한다
        AccountDAO accountDAO = new AccountDAO();
		accountDAO.createAccount(new AccountDTO(number, userid, "save", 0));
		
		
		DepositAccountDTO newDepositAccountDTO = new DepositAccountDTO(
				userid, 
				number,
				new Date(),
				0,
				oneYearLater
		);
		int res = a.createDepositAccount(newDepositAccountDTO);
		
	
		if(res == 1) return true;
		else return false;
	}

	
	//account_id: 깰 곳을 받을 아이디, DepositAccount_id: 예금 계좌아이디
	public boolean transDepositAccounts(String account_id, String DepositAccount_id) {		
		 // 만기된 통장 깨기 -> 예금계좌에 있는 돈에 interest만큼의 이윤을 더한 값을 DepositAccount_id 계좌에 더하기
	    Double interest = 1.8; // 이율
	    DepositAccountDAO depositAccountDAO = new DepositAccountDAO();
	    AccountDAO accountDAO = new AccountDAO();

	    // 만기된 통장 정보 가져오기
	    DepositAccountDTO depositAccountDTO = depositAccountDAO.getDepositAccountByAccountNumber(DepositAccount_id);
	    if (depositAccountDTO == null) {
	        return false; // 만기된 통장이 없는 경우
	    }

	    // 예금 계좌 정보 가져오기
	    AccountDTO accountDTO = accountDAO.getAccount(account_id);
	    if (accountDTO == null) {
	        return false; // 예금 계좌가 없는 경우
	    }
	    
	    AccountDTO d_accountDTO = accountDAO.getAccount(DepositAccount_id);

	    // 예금 계좌 이윤 계산
	    double depositAmount = d_accountDTO.getBalance();
	    double interestAmount = depositAmount * (interest / 100); // 이윤 계산
	    depositAmount += interestAmount;
	    depositAccountDTO.setAmount((int) depositAmount);

	    // 계좌에 예금 게좌 금액 추가
	    double accountBalance = accountDTO.getBalance();
	    accountBalance += depositAccountDTO.getAmount();
	    accountDTO.setBalance(accountBalance);

	    //계좌 업데이트
	    int accountUpdateResult = accountDAO.updateAccount(accountDTO);
	    
	    if(accountUpdateResult == 1) {
	    	depositAccountDAO.deleteExpiredDepositAccounts();
	    	accountDAO.deleteAccount(DepositAccount_id);
	    	return true;
	    }
	    else {
	    	return false;
	    }
	}
	
	
	public List<DepositAccountDTO> viewDepositAccounts(String user_id){
		// 예금통장목록보기(계좌번호)
		DepositAccountDAO depositAccountDAO = new DepositAccountDAO();
		
	    return depositAccountDAO.getDepositAccountsByUser(user_id);
	    
	}
	
	public void createAccount(String user_id, String type){
		AccountDAO accountDAO = new AccountDAO();
		accountDAO.createAccount(new AccountDTO(generateAccountNum(), user_id, type, 0));
	}
	
	
	
}
