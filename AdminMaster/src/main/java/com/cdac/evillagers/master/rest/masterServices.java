package com.cdac.evillagers.master.rest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.evillagers.master.entity.AdminControl;
import com.cdac.evillagers.master.entity.EventPojo;
import com.cdac.evillagers.master.entity.Post;
import com.cdac.evillagers.master.entity.Reply;
import com.cdac.evillagers.master.entity.Status;
import com.cdac.evillagers.master.entity.StatusMessage;
import com.cdac.evillagers.master.repository.RepositoryClass;

@RestController
@CrossOrigin
public class masterServices {
	
	@Autowired
	private RepositoryClass repositoryClass;

	/*
	 * @PostMapping("/reg") void addNew(@RequestBody AdminControl admin) {
	 * admin.setStatus(false); repositoryClass.update(admin); }
	 */
	@PostMapping("/replyonsolve")
	public Status setReply(@RequestBody Reply reply)
	{
		/*Reply reply =  new Reply();
		reply.setContent("problem of road");
		*/
		reply.setDateOfReply(LocalDateTime.now());
		/*reply.setForwardto("@admin2");
		reply.setPostID(20);*/
		if(reply.getForwardfrom()!=null)
		{
			System.out.println(" deswsefs"+reply.getComplaintId());
			int pv = repositoryClass.priLevel(reply.getForwardto());
			System.out.println(pv);
			Post p = repositoryClass.getPost(reply.getComplaintId());
			p.setStatus(pv);
			repositoryClass.update(p);
		}
		repositoryClass.update(reply);
		Status msg = new Status();
		msg.setStatusMessage("Reply Send Successfully");
		msg.setStatusCode(200);
		return msg;
	}
	
	@GetMapping("/getReply")
	public StatusMessage getReply()
	{
		StatusMessage msg = new StatusMessage();
		msg.status=200;
		msg.message=repositoryClass.getReply();
		return msg;
	}
	
	@PostMapping("/myadmins")
	public StatusMessage getAdmins()
	{
		StatusMessage msg = new StatusMessage();
		msg.status=200;
		msg.message=repositoryClass.getReply();
		return msg;
	}
	
	@GetMapping("/lstreply")
	public List<Reply> getData()
	{
		return repositoryClass.getAllReplies();
	}
	
	//
	@PostMapping("/addevent")
	public StatusMessage addEvent(@RequestBody EventPojo event)
	{
		//System.out.println("event calling");
		event.dateofPost=LocalDateTime.now();
		StatusMessage msg = new StatusMessage();
		repositoryClass.update(event);
		msg.status=200;
		msg.message="Event Added Successfully";
		return msg;
	}
	@PostMapping("/getevent")
	public EventPojo getEvent()
	{
		System.out.println("event calling");
		return repositoryClass.getevent();
	}
}
