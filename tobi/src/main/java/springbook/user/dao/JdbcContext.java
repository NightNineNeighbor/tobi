package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {
	Connection c = null;
	PreparedStatement ps = null;

	try {
		c = connectionMaker.makeConnection();

		ps = stmt.makePreparedStatement(c);

		ps.executeQuery();
	} catch (SQLException e) {
		throw e;
	} finally {
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
			}
		}
		if (c != null) {
			try {
				c.close();
			} catch (SQLException e) {
			}
		}
	}
}
