package controller;

import javax.servlet.http.HttpServletRequest;



import javax.servlet.http.HttpServletResponse;

import model.donor.Donor;
import model.donorcodes.DonorDonorCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import repository.DonorRepository;
import utils.PermissionConstants;
import antlr.collections.List;
import backingform.DonorBackingForm;
import backingform.DonorCodeBackingForm;
import backingform.validator.DonorBackingFormValidator;
import backingform.validator.DonorCodeBackingFormValidator;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;


@Controller
public class DonorCodeController {
	
	  @Autowired
	  private DonorRepository donorRepository;
	  
	  @Autowired
	  private UtilController utilController;
	  
         @InitBinder
         protected void initBinder(WebDataBinder binder) {
                binder.setValidator(new DonorCodeBackingFormValidator(binder.getValidator(), donorRepository));
          }

	  @RequestMapping(value = "/updateDonorCodesFormGenerator", method = RequestMethod.GET)
	  @PreAuthorize("hasRole('"+PermissionConstants.EDIT_DONOR_CODE+"')")
	  public ModelAndView updateDonorCodesForm(HttpServletRequest request ,Model model ,@RequestParam(value="donorId") Long donorId){
		  ModelAndView modelAndView = new ModelAndView("donors/updateDonorCodes");  
		  model.addAttribute("addDonorCodeForm", new DonorCodeBackingForm());
		  Donor donor = donorRepository.findDonorById(donorId);
		  modelAndView.addObject("donor", donor);
		  modelAndView.addObject("model", model);
		  modelAndView.addObject("donorFields", utilController.getFormFieldsForForm("donor"));
		  modelAndView.addObject("donorCodeGroups",  donorRepository.getAllDonorCodeGroups());
		  
		  return modelAndView;
		  
	  }
	  
	
	  @RequestMapping(value = "/updateDonorCodes", method = RequestMethod.POST)
	  @PreAuthorize("hasRole('"+PermissionConstants.EDIT_DONOR_CODE+"')")
	  public ModelAndView updateDonorCodes(HttpServletRequest request,HttpServletResponse response, 
			@ModelAttribute("addDonorCodeForm")  @Valid DonorCodeBackingForm form,
                         BindingResult result){
	      
             
                ModelAndView modelAndView = new ModelAndView("donors/donorCodesTable");  
                  if (result.hasErrors()) {
                      modelAndView.addObject("hasErrors", true);
                      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                     modelAndView.addObject("success",false);
	             return modelAndView;
		
                     }
	         DonorDonorCode  donorDonorCode =  new DonorDonorCode();
		 donorDonorCode.setDonorId(donorRepository.findDonorById(form.getDonorId()));
		 donorDonorCode.setDonorCodeId(donorRepository.findDonorCodeById(form.getDonorCodeId()));
		 donorRepository.saveDonorDonorCode(donorDonorCode);
		 modelAndView.addObject("donorDonorCodes", donorRepository.findDonorDonorCodesOfDonor(donorRepository.findDonorById(form.getDonorId())));
	         modelAndView.addObject("success",true);
	         return modelAndView;
		  
	  }
	  
	  @RequestMapping(value = "/donorCodesTable", method = RequestMethod.GET)
	  @PreAuthorize("hasRole('"+PermissionConstants.VIEW_DONOR_CODE+"')")
	  public ModelAndView donorCodesTable(HttpServletRequest request,Long donorId){
		
		  ModelAndView modelAndView = new ModelAndView("donors/donorCodesTable");  
		  modelAndView.addObject("donorDonorCodes", donorRepository.findDonorDonorCodesOfDonor(donorRepository.findDonorById(donorId)));
		  modelAndView.addObject("success",true);
		  return modelAndView;
		  
		  
	  }
	  
	  @RequestMapping(value = "/deleteDonorCode", method = RequestMethod.GET)
	  @PreAuthorize("hasRole('"+PermissionConstants.VOID_DONOR_CODE+"')")
	  public ModelAndView deleteDomorCode(@RequestParam(value="id") Long id){
		  ModelAndView modelAndView = new ModelAndView("donors/donorCodesTable");  
		  Donor donor = donorRepository.deleteDonorCode(id);
		  modelAndView.addObject("donorDonorCodes", donorRepository.findDonorDonorCodesOfDonor(donorRepository.findDonorById(donor.getId())));
		  modelAndView.addObject("success",true);
		  return modelAndView;
		  
	  }
	  
	  
}
