

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;


/**
 * Servlet implementation class Controller
 */
@WebServlet("*.do")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		setHeaders(response);
		actionDo(request, response);

	}
	
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {
	    setHeaders(resp);
	    resp.setStatus(HttpServletResponse.SC_OK);
	}
	
	private void setHeaders(HttpServletResponse response) {
	    response.setHeader("Access-Control-Allow-Origin", "*");
	    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
	    response.setHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
	    response.setHeader("Access-Control-Max-Age", "1728000");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stu
		response.setHeader("Access-Control-Allow-Origin", "*");
		actionDo(request, response);
	}
	
	private void print(HttpServletResponse response) throws IOException {
		response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8"); // 문자열 인코딩 설정
	    
	    // PrintWriter 객체를 가져옵니다.
	    PrintWriter out = response.getWriter();
	    
	    // 문자열을 출력합니다.
	    out.println("Hello, World!");
	    
	    // 출력 스트림을 닫습니다. (서블릿 컨테이너의 구현에 따라 자동으로 닫힐 수도 있습니다.)
	    out.close();
	}
	
    private void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        
    	String jsessionId = request.getParameter("JSESSIONID");
        // 해당 세션 아이디와 연관된 세션 객체 가져오
        String user_id = checklogin(jsessionId);
        
        String com = parse(request);
        Command C = new Command();
        if (com.equals("/join.do")) {
        	
        	print(response);
        	
    		String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            System.out.println(username);
            System.out.println(password);
            C.join(username, password);
            
            response.setStatus(HttpServletResponse.SC_OK);
        	
        }
        else if(user_id != null) {
           // 커맨드별로 severlet 생성
           if (com.equals("/getAccountsByUserAndType.do")) {
               String accountType = request.getParameter("accountType");
               
               System.out.println(accountType + user_id);
               List<AccountDTO> ls_adt = C.getAccountsByUserAndType(user_id, accountType);
               
               JSONArray jsonArray = new JSONArray();
               JSONObject jObject;
               
               for(AccountDTO t : ls_adt) {
            	   
            	   jObject = new JSONObject();
            	   jObject.put("number", t.getAccountId());
            	   jObject.put("money", t.getBalance());
            	   
            	   jsonArray.add(jObject);
               }
               System.out.println(jsonArray.toString());
               response.getWriter().write(jsonArray.toString());
               
               
           }
           //계좌의 잔액확인
           else if (com.equals("/getAccountBalance.do")) {
               String accountId = request.getParameter("accountId");
               double amount = C.getAccountBalance(accountId);
               
               response.getWriter().write(Double.toString(amount));
           } 
           
           //이체 기록 반환
           else if (com.equals("/getTransactionHistory.do")) {
               String accountId = request.getParameter("accountId");
               JSONArray jsonArray = new JSONArray();
               JSONObject jObject;
               List<TransactionDTO> ls_t = C.getTransactionHistory(accountId);
               
               //기록 반환
               for(TransactionDTO t : ls_t) {
            	   System.out.println(t.getAccountId());
            	   jObject = new JSONObject();
            	   jObject.put(t.getFromAccountId(), t.getAmount());
            	   
            	   jsonArray.add(jObject);
               }
               
               response.getWriter().write(jsonArray.toString());
           }
           
           //예금 계좌 조회
           else if (com.equals("/viewDepositAccounts.do")) {
               List<DepositAccountDTO> ls_d = C.viewDepositAccounts(user_id);
               
               
               JSONArray jsonArray = new JSONArray();
               JSONObject jObject;
               
               System.out.println("call view Dep");
               
              
               for(DepositAccountDTO t : ls_d) {
            	   
               //{계좌번호, 만기일, 금액반환}
            	   jObject = new JSONObject();
            	   double money = C.getAccountBalance(t.getAccountNumber());
            	   String date = t.getMaturityDate().toString();
            	   jObject.put("money", money);
            	   jObject.put("date", date);
            	   jObject.put("a_id", t.getAccountNumber()); 	
            	   
            	   jsonArray.add(jObject);
               }
               
               response.getWriter().write(jsonArray.toString());
           } 
           
           //기체기능
           else if (com.equals("/transferMoney.do")) {
               String fromAccountId = request.getParameter("fromAccountId");
               String toAccountId = request.getParameter("toAccountId");
               int amount = Integer.parseInt(request.getParameter("amount"));
               C.transferMoney(fromAccountId, toAccountId, amount);
               
               response.getWriter().write("ok");
           } 
           
           //예금계좌 등록
           else if (com.equals("/applyDepositAccounts.do")) {
               C.applyDepositAccounts(user_id);
           } 
           
           //예금 해지
           else if (com.equals("/transDepositAccounts.do")) {
               String account_id = request.getParameter("account_id");
               String DepositAccount_id = request.getParameter("DepositAccount_id");
               C.transDepositAccounts(account_id, DepositAccount_id);
           }
           //입금 기능
           else if (com.equals("/depositAccounts.do")) {
        	   String account_id = request.getParameter("account_id");
        	   int money = Integer.parseInt(request.getParameter("money"));
        	   
        	   C.depositAccount(account_id, money);
           }
           
           else if (com.equals("/createAccount.do")) {
        	   
               String accountType = request.getParameter("accountType");
               C.createAccount(user_id, accountType);
           }
           else if (com.equals("/createCard.do")) {
        	   
        	   String cardType = request.getParameter("cardType");
        	   String account_id = request.getParameter("accountId");
        	   
        	   
        	   C.createCard(user_id, cardType, account_id);
           }
           
           else if(com.equals("/viewCard.do")) {
        	   List<CardDTO> ls_C = C.viewCard(user_id);
        	   
        	   JSONArray jsonArray = new JSONArray();
               JSONObject jObject;
               
               
               for(CardDTO card : ls_C) {
            	   jObject = new JSONObject();
            	   jObject.put("ctype", card.getCardType());
            	   jObject.put("cid", card.getCardId());
            	   
            	   jsonArray.add(jObject);
               }
               
               response.getWriter().write(jsonArray.toString());
           }
        }
    }
     
     private String parse(HttpServletRequest request){
        String uri = request.getRequestURI();
        String conPath = request.getContextPath();
        String com = uri.substring(conPath.length());
        
        return com;   
     }
     
     
     protected String checklogin(String jsessionId) {
    	 String user_id = LoginTrick.map.get(jsessionId);
    	 System.out.println(jsessionId);
    	 System.out.println(user_id);
    	 
    	return user_id;
     }
     
     
}

