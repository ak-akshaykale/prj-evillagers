package com.cdac.evillagers.master.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.stereotype.Repository;

import com.cdac.evillagers.master.entity.AdminControl;
import com.cdac.evillagers.master.entity.EventPojo;
import com.cdac.evillagers.master.entity.Post;
import com.cdac.evillagers.master.entity.Reply;
import com.cdac.evillagers.master.entity.StatusMessage;
import com.cdac.evillagers.master.entity.UserRegistration;

import ch.qos.logback.core.status.Status;

@Repository
public class RepositoryClass {

	@PersistenceContext
	private EntityManager entityManager;
	
	@org.springframework.transaction.annotation.Transactional
	public void update(Object reqObj)
	{
		entityManager.merge(reqObj);
	}
	
	public void changeStatus(Object obj)
	{
		update(obj);
	}
	public Reply getReply()
	{
		int pid=1;
		try
		{
			return (Reply)entityManager.createQuery("select r from Reply r where r.postID=:PID")
					.setParameter("PID",pid).getSingleResult();
		}
		catch (Exception e) {
			return null;
		}
		
	}
	public List<Post> getPosts(int prilevel)
	{
		try
		{
			return (List<Post>)entityManager.createQuery("select r from Post r where r.status=:PID")
					.setParameter("PID",prilevel).getResultList();
		}
		catch (Exception e) {
			return null;
		}
		
	}
	public List<Reply> getAllReplies()
	{
		try
		{
		List<Reply> lstReply = entityManager.createQuery("select r from Reply r").getResultList();
		return lstReply;
		}
		catch (Exception e) {
			return null;
		}
	}
	
	public AdminControl getVerify(String name)
	{
		try
		{
		return (AdminControl)entityManager.createQuery("select a from AdminControl a where a.adminName=:admn and a.status=1")
		.setParameter("admn",name).getSingleResult();
		}
		catch (Exception e) {
			return null;
		}
	}
	public List<String> getAllAdmin(AdminControl adm)
	{
		try
		{
		return (List<String>)entityManager.createQuery("select a.adminName from AdminControl a where a.privilegeLevel<=:plv")
				.setParameter("plv",adm.getPrivilegeLevel()).getResultList();
		}
		catch (Exception e) {
			return null;
		}
	}
	public AdminControl getAdmin(String name)
	{
		try
		{
		return (AdminControl)entityManager.createQuery("select a from AdminControl a where a.adminName=:admn")
				.setParameter("admn", name).getSingleResult();
		}
		catch (Exception e) {
			return null;
		}
	}
	public int isValidAdmin(String username, String password) {
		
		AdminControl a = new AdminControl();
		a.setPassword(password);
		password=""+a.getPassword().hashCode();
		System.out.println(" ds"+password);
		try {
		List<AdminControl> ul = (List<AdminControl>) entityManager
				.createQuery("select c from AdminControl c where c.adminName=:un and c.password=:pwd")
				.setParameter("un", username).setParameter("pwd", password).getResultList();
		//System.out.println(ul.size());
		if(ul.get(0).isStatus())
		{
			return ul.size();
		}
		else {
			return -1;
		}
		}
		catch(Exception e)
		{
			return 0;
		}
	}
	public int getNoofAdmins()
	{
		List<AdminControl> lst =(List<AdminControl>)entityManager.createQuery("select a from AdminControl a where a.adminName='@akmaster'").getResultList();
		return lst.size();
	}
	
	public int priLevel(String name)
	{
		return ((AdminControl)entityManager.createQuery("select a from AdminControl a where a.adminName=:admn")
				.setParameter("admn", name).getSingleResult()).getPrivilegeLevel();
	}
	public Post getPost(int complaintID)
	{
		return (Post)entityManager.createQuery("select a from Post a where a.complaintId=:admn")
				.setParameter("admn", complaintID).getSingleResult();
	}
	
	public EventPojo getevent()
	{
		return (EventPojo)entityManager.createQuery("select a from EventPojo a")
				.getSingleResult();
	}
}
