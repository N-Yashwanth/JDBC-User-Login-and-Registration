package jdbc;

import java.util.*;
import java.sql.*;

public class UserRegistrationNew {
	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in);
		System.out.println("Enter choice 1:Registration, 2:Login");
		int ch=scan.nextInt();
		switch (ch) {
		case 1:
			System.out.println("Enter username");
			String username=scan.next();
			System.out.println("Enter password");
			String password=scan.next();
			register(username,password);
			break;
		case 2:
			System.out.println("Enter username");
			String username1=scan.next();
			System.out.println("Enter password");
			String password1=scan.next();
			if(login(username1,password1)) {
				System.out.println("Login Successfull");
			}
			else {
				System.out.println("Invalid user");
			}
			break;
		default:
			System.out.println("Wrong choice press 1 (or) 2");
			break;
		}
	}

	public static void register(String username, String password) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/userdetails","root","root");
			PreparedStatement pst=con.prepareStatement("insert into users (userName,password) values (?,?)");
			pst.setString(1, username);
			pst.setString(2, password);
			pst.execute();
			System.out.println("Registered successfully");
			con.close();
			pst.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}

	public static boolean login(String username, String password) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/userdetails","root","root");
			PreparedStatement pst=con.prepareStatement("select password from users where username=?");
			pst.setString(1, username);
			try {
				ResultSet user=pst.executeQuery();
				if(user.next()) {
					String storedPassword=user.getString("password");
					return storedPassword.equals(password);
				}
			}catch(Exception e) {
				System.out.println(e);
			}
			pst.close();
			con.close();
		}catch(Exception e) {
			System.out.println(e);
		}
		return false;
	}
}
