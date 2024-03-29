package de.hwg_lu.bw4s.beansPruefung;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.hwg_lu.bw.jdbc.NoConnectionException;
import de.hwg_lu.bw.jdbc.PostgreSQLAccess;

public class AccountBean {
	String userid;
	String password;
	String active;
	String admin;
	String username;
	String email;
boolean accoutcheck;
String updat="";
	Connection dbConn;
	
	public String getUpdat() {
		return updat;
	}

	public void setUpdat(String updat) {
		this.updat = updat;
	}

	public Connection getDbConn() {
		return dbConn;
	}

	public void setDbConn(Connection dbConn) {
		this.dbConn = dbConn;
	}

	public AccountBean(){
//		this.dbConn = new PostgreSQLAccess().getConnection();
		this.userid   = "";
		this.password = "";
		this.active   = "";
		this.admin    = "";
		this.username = "";
		this.email    = "";
	}
	
	public boolean isAccoutcheck() {
		return accoutcheck;
	}

	public void setAccoutcheck(boolean accoutcheck) {
		this.accoutcheck = accoutcheck;
	}
	public void setUpdatePasswordforgot( String passwd) throws SQLException {
		String sql="UPDATE bwi520_632134.accounts SET password='"+ passwd+"' WHERE userid='"+ this.getEmail()+"';";
		System.out.println(sql);
		Connection dbConn= new PostgreSQLAccess().getConnection();
		PreparedStatement prep = dbConn.prepareStatement(sql);
		
		prep.executeUpdate();
		System.out.println("passwort wurde ge�ndert");
		
	}
	boolean vorhanden =false;
	public boolean getPasswordforgot() throws SQLException {
		
		String sql = "select email from accounts where userid = ?";
		this.dbConn = new PostgreSQLAccess().getConnection();
		PreparedStatement prep = this.dbConn.prepareStatement(sql);
		prep.setString(1, getEmail());
		ResultSet dbRes =prep.executeQuery();
		
		if(dbRes.next()) {
			 vorhanden =true;
			
		}
		
		return vorhanden;
		
	}
	boolean passvergessen= false;
	public boolean isPassvergessen() {
		return passvergessen;
	}

	public void setPassvergessen(boolean passvergessen) {
		this.passvergessen = passvergessen;
	}

	public  String getLoggin() throws SQLException {
		
		
		if (updat!="") {
			
			return updat;
		}
		if (passvergessen== true) {
			System.out.println("passwort ist vergessen!");
		
			String html = "<br>\r\n"
					+ "					<br>\r\n"
					+ "					<br>\r\n"
					+ "					<div class=\"group\">\r\n"
					+ "					<label for=\"user\" class=\"label\">enter your Email please!</label>\r\n"
					+ "					<input  name=\"emailreset\" type=\"text\" value=\"\" class=\"input\" title=\"enter your Email please!\" \">\r\n"
					+ "					<br>\r\n"
					+ "					<br>\r\n"
					+ "					<input class=\"button\" type=\"submit\" type=\"text\" name=\"reset\" value=\"passwort zuruesetzen\" title=\"reset password\" >\r\n"
					+ "					</div>\r\n"
					+ "					<br>";
			return html ;
		}
		if (accoutcheck) {
			if (!vorhanden) {
				return"<br><br><br> Account not found sorry";
			}
			
			System.out.println(getEmail()+" ich existiere from aucount chekDB");
			String html = "<br>"
					+ "					<br>\r\n"
					+ "					<br>\r\n"
					+ "					<div class=\"group\">\r\n"
					+ "					<label for=\"user\" class=\"label\">enter your New password please!</label>\r\n"
					+ "					<input  name=\"passreset1\" type=\"password\" value=\"\"   class=\"input\" title=\"enter a new Password please!\" \">\r\n"
					+ "					<br><label for=\"user\" class=\"label\">confirm the password please!</label>\r\n"

					+ "					<input  name=\"passreset2\" type=\"password\" value=\"\" class=\"input\" title=\"confirm the password  please!\" \">\r\n"
					+ "					<br>\r\n"
					+ "					<br>\r\n"
					+ "					<input class=\"button\" type=\"submit\"  name=\"newpass\" value=\"passwort Bestaetigen\" title=\"reset password\" >\r\n"
					+ "					</div>\r\n"
					+ "					<br>";
			System.out.println("accout existiert!");
			return html;
			}
		
		
		if (accoutcheck=false) {
			return "<br><br><br>Account not found";
		}
			return"";		
		
		
		
	}
	
	public boolean insertAccountIfNotExists() throws SQLException {
		// true - Datensatz existierte noch nicht und wurde geschrieben
		// false - Datensatz existierte schon und wurde nicht geschrieben
		this.prepareAttributesForDB();
		// erst pr�fen, ob ex.
		// wenn ja, dann return false
		// wenn nein, dann insert ohne weitere Pr�fung und return true
		boolean accountExists = this.checkAccountExists();
		if (accountExists) return false;
		else {
			this.insertAccountNoCheck();
			return true;
		}
	}
	
	public boolean checkAccountExists3() throws SQLException{
		// true - this.userid wurde in der DB-Tabelle account gefunden
		// false - this.userid wurde in account nicht gefunden
		String sql = "select userid from accounts";
		this.dbConn = new PostgreSQLAccess().getConnection();
		PreparedStatement prep = this.dbConn.prepareStatement(sql);
		ResultSet dbRes = prep.executeQuery();
		boolean gefunden = false;
		while (dbRes.next()){
			String currentUserid = dbRes.getString("userid").trim();
			if (currentUserid.equals(this.userid)){
				gefunden = true;
				break;
			}
		}
		return gefunden;		
	}
	
	public boolean checkAccountExists2() throws SQLException{
		// true - this.userid wurde in der DB-Tabelle account gefunden
		// false - this.userid wurde in account nicht gefunden
		String sql = "select count(*) from accounts where userid = ?";
		this.dbConn = new PostgreSQLAccess().getConnection();
		PreparedStatement prep = this.dbConn.prepareStatement(sql);
		prep.setString(1, this.userid);
		ResultSet dbRes = prep.executeQuery();
		//dbRes enth�lt 1 Zeile, 1 Spalte mit Inhalt 0 oder 1
//		boolean gefunden = false;
//		dbRes.next();
//		int anzLines = dbRes.getInt(1);
//		if (anzLines > 0) gefunden = true;
//		return gefunden;
		dbRes.next();
		return (dbRes.getInt(1) > 0);
	}

	public boolean checkAccountExists() throws NoConnectionException, SQLException {
		// true - this.userid wurde in der DB-Tabelle account gefunden
		// false - this.userid wurde in account nicht gefunden
		String sql = "select userid from accounts where userid = ?";
		this.dbConn = new PostgreSQLAccess().getConnection();
		PreparedStatement prep = this.dbConn.prepareStatement(sql);
		prep.setString(1, this.userid);
		ResultSet dbRes = prep.executeQuery();
		boolean gefunden = dbRes.next();
		return gefunden;
	}
	
	public void prepareAttributesForDB() {
		if (this.userid == null) this.userid = "";
		if (this.password == null) this.password = "";
		if (this.username == null) this.username = "";
		if (this.email == null) this.email = "";
		if (this.active == null) this.active = "";
		if (this.admin == null) this.admin = "";
		//wir schneiden ab, in der Praxis w�rde man eine Exception werfen
		if (this.userid.length() > 56) this.userid = this.userid.substring(0,56);
		if (this.password.length() > 32) this.password = this.password.substring(0,32);
		if (this.username.length() > 256) this.username = this.username.substring(0,256);
		if (this.email.length() > 256) this.email = this.email.substring(0,256);
		if (this.active.toLowerCase().equals("yes") ||
				this.active.equalsIgnoreCase("y") ||
				this.active.equalsIgnoreCase("ja") ||
				this.active.equalsIgnoreCase("j")) this.active = "Y";
		else this.active = "N";
		if (this.admin.toLowerCase().equals("yes") ||
				this.admin.equalsIgnoreCase("y") ||
				this.admin.equalsIgnoreCase("ja") ||
				this.admin.equalsIgnoreCase("j")) this.admin = "Y";
		else this.admin = "N";
	}

	public void insertAccountNoCheck() throws NoConnectionException, SQLException {
		String sql = "insert into accounts (userid, password, active, admin, username, email) values (?, ?, ?, ?, ?, ?)";
		System.out.println(sql);
		this.dbConn = new PostgreSQLAccess().getConnection();
		PreparedStatement prep = this.dbConn.prepareStatement(sql);
		prep.setString(1, this.userid);
		prep.setString(2, this.password);
		prep.setString(3, this.active);
		prep.setString(4, this.admin);
		prep.setString(5, this.username);
		prep.setString(6, this.email);
		prep.executeUpdate();
		System.out.println("Account " + this.userid + " erfolgreich angelegt");
	}
	
	public String toString(){
		String s = "AccountBean mit ";
				s+= "userid: "   + this.getUserid() + ", ";
				s+= "password: " + this.getPassword() + ", ";
				s+= "active: "   + this.getActive() + ", ";
				s+= "admin: "    + this.getAdmin() + ", ";
				s+= "username: " + this.getUsername() + ", ";
				s+= "email: "    + this.getEmail();				
		return s;
	}
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}	
}
