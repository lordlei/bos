package crm.service;

import crm.domain.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Transactional
public class CustomerServiceImpl implements ICustomerService {
	private JdbcTemplate jdbcTemplate;
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Customer> findAll() {
		String sql = "select * from t_customer";
		List<Customer> list = jdbcTemplate.query(sql, new RowMapper<Customer>() {
			@Override
			public Customer mapRow(ResultSet rs, int i) throws SQLException {
				int id = rs.getInt("id");//根据字段名称从结果集中获取对应的值
				String name = rs.getString("name");
				String station = rs.getString("station");
				String telephone = rs.getString("telephone");
				String address = rs.getString("address");
				String decidedzone_id = rs.getString("decidedzone_id");
				return new Customer(id, name, station, telephone, address, decidedzone_id);
			}
		});
		return list;
	}

    public List<Customer> findListNotAssociation() {
        String sql = "select * from t_customer where decidedzone_id is NULL ";
        List<Customer> list = jdbcTemplate.query(sql, new RowMapper<Customer>() {
            @Override
            public Customer mapRow(ResultSet rs, int i) throws SQLException {
                int id = rs.getInt("id");//根据字段名称从结果集中获取对应的值
                String name = rs.getString("name");
                String station = rs.getString("station");
                String telephone = rs.getString("telephone");
                String address = rs.getString("address");
                String decidedzone_id = rs.getString("decidedzone_id");
                return new Customer(id, name, station, telephone, address, decidedzone_id);
            }
        });
        return list;
    }

    @Override
    public List<Customer> findListHasAssociation(String decidedzoneId) {
        String sql = "select * from t_customer where decidedzone_id = ? ";
        List<Customer> list = jdbcTemplate.query(sql, new RowMapper<Customer>() {
            @Override
            public Customer mapRow(ResultSet rs, int i) throws SQLException {
                int id = rs.getInt("id");//根据字段名称从结果集中获取对应的值
                String name = rs.getString("name");
                String station = rs.getString("station");
                String telephone = rs.getString("telephone");
                String address = rs.getString("address");
                String decidedzone_id = rs.getString("decidedzone_id");
                return new Customer(id, name, station, telephone, address, decidedzone_id);
            }
        },decidedzoneId);
        return list;
    }

    @Override
    public void assigncustomerstodecidedzone(String decidedzoneId, Integer[] customerIds) {
        String sql1 = "update t_customer set decidedzone_id = null where decidedzone_id=?";
        jdbcTemplate.update(sql1,decidedzoneId);
        String sql2 = "update t_customer set decidedzone_id=? where id=?";
        for (Integer id : customerIds) {
            jdbcTemplate.update(sql2,decidedzoneId,id);
        }
    }

    @Override
    public Customer findCustomerByTelephone(String telephone) {
        String sql = "select * from t_customer where telephone = ? ";
        List<Customer> list = jdbcTemplate.query(sql, new RowMapper<Customer>() {
            @Override
            public Customer mapRow(ResultSet rs, int i) throws SQLException {
                int id = rs.getInt("id");//根据字段名称从结果集中获取对应的值
                String name = rs.getString("name");
                String station = rs.getString("station");
                String telephone = rs.getString("telephone");
                String address = rs.getString("address");
                String decidedzone_id = rs.getString("decidedzone_id");
                return new Customer(id, name, station, telephone, address, decidedzone_id);
            }
        },telephone);
        if (list!=null&&list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }


    @Override
    public String findDecidedzoneByAddress(String address) {
        String sql = "select decidedzone_id from t_customer where address = ? ";
        String decidedzoneId = jdbcTemplate.queryForObject(sql, String.class, address);
        return decidedzoneId;
    }


}


