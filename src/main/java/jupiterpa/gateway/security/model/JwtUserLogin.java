package jupiterpa.gateway.security.model;

public class JwtUserLogin {
    String userName;
    String role;
    String key;
    
    public JwtUserLogin() {}
    
    public JwtUserLogin(String userName, String role, String key) {
    	this.userName = userName;
    	this.role = role;
    	this.key = key;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public String getRole() {
        return role;
    }

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	@Override 
	public String toString() {
		return "Username=" + userName + " Role=" + role + " key=" + key;
	}
    
}
