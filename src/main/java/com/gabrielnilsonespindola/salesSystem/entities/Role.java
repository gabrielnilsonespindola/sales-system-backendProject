package com.gabrielnilsonespindola.salesSystem.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_role")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Long roleId;

	private String roleName;
	
	public Role() {
	}

	public Role(Long roleId, String roleName) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
	}

	public Long getRoleid() {
		return roleId;
	}

	public void setRoleid(Long roleid) {
		this.roleId = roleid;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(roleName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		return Objects.equals(roleName, other.roleName);
	}
	
	
	 public enum Values {

	        ADMIN(1L),
	        BASIC(2L);

	        long roleId;

	        Values(){	        	
	        }
	        
	        Values(long roleId) {
	            this.roleId = roleId;
	        }

	        public long getRoleId() {
	            return roleId;
	        }
		
		
		
	}
	
	

}
