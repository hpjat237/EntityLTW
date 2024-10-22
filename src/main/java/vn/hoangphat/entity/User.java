package vn.hoangphat.entity;

import java.io.Serializable;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import vn.hoangphat.configs.JPAConfig;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "User")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")

public class User implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "fullname", columnDefinition = "nvarchar(555) NOT NULL")
	@NotEmpty(message = "Không được phép rỗng")
	private String fullname;
	
	@Column(name = "username", columnDefinition = "nvarchar(200) NOT NULL")
	@NotEmpty(message = "Không được phép rỗng")
	private String username;
	
	@Column(name = "password", columnDefinition = "nvarchar(200) NOT NULL")
	@NotEmpty(message = "Không được phép rỗng")
	private String password;
	
	@Column(name = "images", columnDefinition = "nvarchar(555)")
	private String images;
	
	@Column(name = "phone", columnDefinition = "nvarchar(50) NOT NULL")
	private String phone;
	
	@Column(name = "email", columnDefinition = "nvarchar(100) NOT NULL")
	private String email;
	
	@Column(name = "roleid")
	private int roleid;
	
	public static void main(String[] args) {
		EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin();
			
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback();
			throw e;
		} finally {
			enma.close();
		}
	}
}
