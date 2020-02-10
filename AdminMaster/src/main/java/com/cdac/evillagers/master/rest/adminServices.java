package com.cdac.evillagers.master.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.evillagers.master.entity.AdminControl;
import com.cdac.evillagers.master.entity.Post;
import com.cdac.evillagers.master.entity.Resetpass;
import com.cdac.evillagers.master.entity.StatusMessage;
import com.cdac.evillagers.master.repository.RepositoryClass;

@RestController
@CrossOrigin
public class adminServices {
	@Autowired
	RepositoryClass resClass;
	/*@GetMapping("/noa")
	public int getnoofadmin()
	{
		return resClass.getNoofAdmins();
	}*/
	
	@PostMapping("/reg")
	public StatusMessage addNew(@RequestBody AdminControl admin)
	{
		System.out.println(admin.getVillage());
		int apass = admin.getPassword().hashCode();
		admin.setPassword(""+apass);
		System.out.println("Adm:"+admin.getPassword());
		StatusMessage msg = new StatusMessage();
		if(resClass.getAdmin(admin.getAdminName())==null)
		{	
		//admin.setStatus(false);
		resClass.update(admin);
		msg.status=200;
		msg.message="Admin Member Added Successfully";
		System.out.println("reg");
		}
		else
		{
			//System.out.println("Not reg");
			msg.status=404;
			msg.message="Admin Member Already Register";
		}
		return msg;
	}
	@PostMapping("/update")
	public StatusMessage updateAdmin(@RequestBody AdminControl admin)
	{
		System.out.println(admin.getAdminName()+" "+admin.getPrivilegeLevel());
		
		AdminControl adm = resClass.getAdmin(admin.getAdminName());
		admin.setId(adm.getId());
		StatusMessage msg = new StatusMessage();
		if(adm!=null)
		{	
		resClass.update(admin);
		msg.status=200;
		msg.message="Admin Information Updated Successfully";
		}
		else
		{
			msg.status=404;
			msg.message="Admin Information Update Failed";
		}
		return msg;
	}
	@PostMapping("/verify")
	public StatusMessage verifyAdmin(@RequestBody AdminControl admin)
	{
		AdminControl adm = resClass.getVerify(admin.getAdminName());
		StatusMessage msg = new StatusMessage();
		
		if(adm!=null)
		{
			adm.setPrivilegeLevel(admin.getPrivilegeLevel());
			adm.setStatus(admin.isStatus());
			adm.setPassword(admin.getPassword());
			//if(admin.isStatus())
			resClass.update(adm);
			msg.status=200;
			msg.message="Admin Member Added Successfully";
			return msg;
		}
		msg.message="Memeber Verification Failed";
		msg.status=404;
		return msg;
	}
	
	@PostMapping("/adminfetch")
	public AdminControl getAdmin(@RequestBody AdminControl admin)
	{
		return resClass.getAdmin(admin.getAdminName());
	}
	
	@PostMapping("/getposts")
	public List<Post> getPosts(@RequestBody AdminControl admin)
	{
		//	int prilevel=1;
		 return resClass.getPosts(admin.getPrivilegeLevel());
	}
	
	@PostMapping("/admins")
	public List<String> getAdmins(@RequestBody AdminControl admin)
	{
		return resClass.getAllAdmin(admin);
	}
	
	@PostMapping("/resetpass")
	public StatusMessage resetPassword(@RequestBody Resetpass reset)
	{
		AdminControl a = new AdminControl();
		a.setPassword(""+reset.getOldpass().hashCode());
		reset.setOldpass(""+a.getPassword().hashCode());
		a.setPassword(""+reset.getNewpass().hashCode());
		reset.setNewpass(""+a.getPassword().hashCode());
		
		AdminControl adm = resClass.getVerify(reset.getName());
		StatusMessage msg = new StatusMessage();
		if(adm!=null)
		{
			if(adm.getPassword().equals(reset.getOldpass()))
			{
			adm.setPassword(reset.getNewpass());
			resClass.update(adm);
			msg.status=200;
			msg.message="Password Updated Successfully";
			return msg;
			}
		}
		msg.message="Password Reset Failed";
		msg.status=404;
		return msg;
	}
	
	@GetMapping("/notify")
	public String getService()
	{
		System.out.println("Req.. 4200");
		return "";
	}
	boolean isAuthorized()
	{
		return false;
	}
}
