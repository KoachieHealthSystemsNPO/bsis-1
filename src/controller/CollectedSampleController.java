package controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import model.admin.ConfigPropertyConstants;
import model.collectedsample.CollectedSample;
import model.collectedsample.CollectedSampleBackingForm;
import model.collectedsample.CollectedSampleBackingFormValidator;
import model.collectedsample.CollectionsWorksheetForm;
import model.collectedsample.FindCollectedSampleBackingForm;
import model.donor.Donor;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import repository.BloodBagTypeRepository;
import repository.BloodTestRepository;
import repository.CollectedSampleRepository;
import repository.DonorRepository;
import repository.DonorTypeRepository;
import repository.GenericConfigRepository;
import repository.LocationRepository;
import repository.SequenceNumberRepository;
import viewmodel.CollectedSampleViewModel;

@Controller
public class CollectedSampleController {
  @Autowired
  private CollectedSampleRepository collectedSampleRepository;

  @Autowired
  private LocationRepository locationRepository;

  @Autowired
  private BloodBagTypeRepository bloodBagTypeRepository;

  @Autowired
  private DonorTypeRepository donorTypeRepository;

  @Autowired
  private DonorRepository donorRepository;

  @Autowired
  private BloodTestRepository bloodTestRepository;

  @Autowired
  private SequenceNumberRepository sequenceNumberRepository;

  @Autowired
  private GenericConfigRepository genericConfigRepository;
  
  @Autowired
  private UtilController utilController;

  public CollectedSampleController() {
  }

  @InitBinder
  protected void initBinder(WebDataBinder binder) {
    binder.setValidator(new CollectedSampleBackingFormValidator(binder.getValidator(),
                        utilController));
  }

  public static String getUrl(HttpServletRequest req) {
    String reqUrl = req.getRequestURL().toString();
    String queryString = req.getQueryString();   // d=789
    if (queryString != null) {
        reqUrl += "?"+queryString;
    }
    return reqUrl;
  }

  private String getNextPageUrl(HttpServletRequest request) {
    String reqUrl = request.getRequestURL().toString().replaceFirst("findCollection.html", "findCollectionPagination.html");
    String queryString = request.getQueryString();   // d=789
    if (queryString != null) {
        reqUrl += "?"+queryString;
    }
    return reqUrl;
  }

  @RequestMapping(value = "/findCollectionFormGenerator", method = RequestMethod.GET)
  public ModelAndView findCollectionFormGenerator(HttpServletRequest request, Model model) {

    FindCollectedSampleBackingForm form = new FindCollectedSampleBackingForm();
    model.addAttribute("findCollectedSampleForm", form);

    ModelAndView mv = new ModelAndView("findCollectionForm");
    Map<String, Object> m = model.asMap();
    addEditSelectorOptions(m);
    utilController.addTipsToModel(model.asMap(), "collectedSamples.find");
    // to ensure custom field names are displayed in the form
    m.put("collectedSampleFields", utilController.getFormFieldsForForm("collectedSample"));
    m.put("refreshUrl", getUrl(request));
    mv.addObject("model", m);
    return mv;
  }

  @RequestMapping("/findCollection")
  public ModelAndView findCollection(HttpServletRequest request,
      @ModelAttribute("findCollectionForm") FindCollectedSampleBackingForm form,
      BindingResult result, Model model) {

    List<CollectedSample> collections = Arrays.asList(new CollectedSample[0]);

    ModelAndView modelAndView = new ModelAndView("collectionsTable");
    Map<String, Object> m = model.asMap();
    m.put("tableName", "findProductsTable");
    m.put("collectedSampleFields", utilController.getFormFieldsForForm("collectedSample"));
    m.put("allCollectedSamples", getCollectionViewModels(collections));
    m.put("refreshUrl", getUrl(request));
    m.put("nextPageUrl", getNextPageUrl(request));
    m.put("saveAsWorksheetUrl", getWorksheetUrl(request));
    addEditSelectorOptions(m);

    modelAndView.addObject("model", m);
    return modelAndView;
  }

  private String getWorksheetUrl(HttpServletRequest request) {
    String worksheetUrl = request.getRequestURL().toString().replaceFirst("findCollection.html", "saveAsWorksheet.html");
    String queryString = request.getQueryString();   // d=789
    if (queryString != null) {
        worksheetUrl += "?" + queryString;
    }
    return worksheetUrl;
  }

  /**
   * Get column name from column id, depends on sequence of columns in collectionsTable.jsp
   */
  private String getSortingColumn(int columnId, Map<String, Object> formFields) {

    List<String> visibleFields = new ArrayList<String>();
    visibleFields.add("id");
    for (String field : Arrays.asList("collectionNumber", "collectedOn","bloodBagType", "collectionCenter", "collectionSite")) {
      Map<String, Object> fieldProperties = (Map<String, Object>) formFields.get(field);
      if (fieldProperties.get("hidden").equals(false))
        visibleFields.add(field);
    }

    Map<String, String> sortColumnMap = new HashMap<String, String>();
    sortColumnMap.put("id", "id");
    sortColumnMap.put("collectionNumber", "collectionNumber");
    sortColumnMap.put("collectedOn", "collectedOn");
    sortColumnMap.put("bloodBagType", "bloodBagType.bloodBagType");
    sortColumnMap.put("collectionCenter", "collectionCenter.name");
    sortColumnMap.put("collectionSite", "collectionSite.name");
    String sortColumn = visibleFields.get(columnId);

    if (sortColumnMap.get(sortColumn) == null)
      return "id";
    else
      return sortColumnMap.get(sortColumn);
  }

  @RequestMapping("/findCollectionPagination")
  public @ResponseBody Map<String, Object> findCollectionPagination(HttpServletRequest request,
      @ModelAttribute("findCollectedSampleForm") FindCollectedSampleBackingForm form,
      BindingResult result, Model model) {

    Map<String, Object> pagingParams = utilController.parsePagingParameters(request);
    int sortColumnId = (Integer) pagingParams.get("sortColumnId");
    Map<String, Object> formFields = utilController.getFormFieldsForForm("collectedSample");
    pagingParams.put("sortColumn", getSortingColumn(sortColumnId, formFields));

    String collectionNumber = form.getCollectionNumber();
    if (collectionNumber != null)
      collectionNumber = collectionNumber.trim();
    String dateCollectedFrom = form.getDateCollectedFrom();
    String dateCollectedTo = form.getDateCollectedTo();

    List<Integer> bloodBagTypeIds = new ArrayList<Integer>();
    bloodBagTypeIds.add(-1);
    if (form.getBloodBagTypes() != null) {
      for (String bloodBagTypeId : form.getBloodBagTypes()) {
        bloodBagTypeIds.add(Integer.parseInt(bloodBagTypeId));
      }
    }

    List<Long> centerIds = new ArrayList<Long>();
    centerIds.add((long) -1);
    if (form.getCollectionCenters() != null) {
      for (String center : form.getCollectionCenters()) {
        centerIds.add(Long.parseLong(center));
      }
    }

    List<Long> siteIds = new ArrayList<Long>();
    siteIds.add((long) -1);
    if (form.getCollectionSites() != null) {
      for (String site : form.getCollectionSites()) {
        siteIds.add(Long.parseLong(site));
      }
    }

    List<Object> results = collectedSampleRepository.findCollectedSamples(
                                        form.getCollectionNumber(),
                                        bloodBagTypeIds, centerIds, siteIds,
                                        dateCollectedFrom, dateCollectedTo, pagingParams);

    @SuppressWarnings("unchecked")
    List<CollectedSample> collectedSamples = (List<CollectedSample>) results.get(0);
    Long totalRecords = (Long) results.get(1);

    return generateDatatablesMap(collectedSamples, totalRecords, formFields);
  }

  /**
   * Datatables on the client side expects a json response for rendering data from the server
   * in jquery datatables. Remember of columns is important and should match the column headings
   * in collectionsTable.jsp.
   */
  private Map<String, Object> generateDatatablesMap(List<CollectedSample> collectedSamples, Long totalRecords, Map<String, Object> formFields) {
    Map<String, Object> collectionsMap = new HashMap<String, Object>();

    ArrayList<Object> collectionList = new ArrayList<Object>();

    for (CollectedSampleViewModel collection : getCollectionViewModels(collectedSamples)) {

      List<Object> row = new ArrayList<Object>();
      
      row.add(collection.getId().toString());

      for (String property : Arrays.asList("collectionNumber", "collectedOn", "bloodBagType", "collectionCenter", "collectionSite")) {
        if (formFields.containsKey(property)) {
          Map<String, Object> properties = (Map<String, Object>)formFields.get(property);
          if (properties.get("hidden").equals(false)) {
            String propertyValue = property;
            try {
              propertyValue = BeanUtils.getProperty(collection, property);
            } catch (IllegalAccessException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            } catch (InvocationTargetException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            } catch (NoSuchMethodException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
            row.add(propertyValue.toString());
          }
        }
      }

      collectionList.add(row);
    }
    collectionsMap.put("aaData", collectionList);
    collectionsMap.put("iTotalRecords", totalRecords);
    collectionsMap.put("iTotalDisplayRecords", totalRecords);
    return collectionsMap;
  }

  private void addEditSelectorOptions(Map<String, Object> m) {
    m.put("centers", locationRepository.getAllCenters());
    m.put("donorTypes", donorTypeRepository.getAllDonorTypes());
    m.put("bloodBagTypes", bloodBagTypeRepository.getAllBloodBagTypes());
    m.put("sites", locationRepository.getAllCollectionSites());
  }

  @RequestMapping(value = "/addCollectionFormForDonorGenerator", method = RequestMethod.GET)
  public ModelAndView addCollectionFormForDonorGenerator(HttpServletRequest request,
      Model model,
      @RequestParam(value="donorId", required=true) String donorId) {

    Map<String, Object> m = model.asMap();
    addEditSelectorOptions(m);
    m.put("refreshUrl", getUrl(request));
    m.put("collectionForDonor", true);
    m.put("existingCollectedSample", false);
    // to ensure custom field names are displayed in the form
    Map<String, Object> formFields = utilController.getFormFieldsForForm("collectedSample");
    m.put("collectedSampleFields", formFields);
    m.put("disallowDonorChange", true);

    CollectedSampleBackingForm form = new CollectedSampleBackingForm();
    setCollectionNumber(form, (Map<String, Object>) formFields.get("collectionNumber"));

    m.put("editCollectedSampleForm", form);

    Donor donor = donorRepository.findDonorById(donorId);
    form.setDonor(donor);

    ModelAndView mv = new ModelAndView("editCollectedSampleForm");
    mv.addObject("model", m);
    return mv;
  }

  private void setCollectionNumber(CollectedSampleBackingForm form,
      Map<String, Object> collectionNumberProperties) {
    boolean isAutoGeneratable = (Boolean) collectionNumberProperties.get("isAutoGeneratable");
    boolean autoGenerate = (Boolean) collectionNumberProperties.get("autoGenerate");
    if (isAutoGeneratable && autoGenerate)
      form.setCollectionNumber(sequenceNumberRepository.getNextCollectionNumber());    
  }

  @RequestMapping(value = "/editCollectionFormGenerator", method = RequestMethod.GET)
  public ModelAndView editCollectionFormGenerator(HttpServletRequest request,
      Model model,
      @RequestParam(value="collectionId", required=false) Long collectionId) {

    CollectedSampleBackingForm form = new CollectedSampleBackingForm();

    ModelAndView mv = new ModelAndView("editCollectedSampleForm");
    Map<String, Object> m = model.asMap();
    m.put("refreshUrl", getUrl(request));
    m.put("existingCollectedSample", false);
    if (collectionId != null) {
      form.setId(collectionId);
      CollectedSample collectedSample = collectedSampleRepository.findCollectedSampleById(collectionId);
      if (collectedSample != null) {
        form = new CollectedSampleBackingForm(collectedSample);
        m.put("existingCollectedSample", true);
      }
      else {
        form = new CollectedSampleBackingForm();
      }
    }
    addEditSelectorOptions(m);
    m.put("editCollectedSampleForm", form);
    m.put("refreshUrl", getUrl(request));
    // to ensure custom field names are displayed in the form
    Map<String, Object> formFields = utilController.getFormFieldsForForm("collectedSample");
    m.put("collectedSampleFields", formFields);
    if (m.get("existingCollectedSample").equals(false))
      setCollectionNumber(form, (Map<String, Object>) formFields.get("collectionNumber"));
    mv.addObject("model", m);
    return mv;
  }

  @RequestMapping(value = "/addCollectedSample", method = RequestMethod.POST)
  public ModelAndView addCollectedSample(
      HttpServletRequest request,
      HttpServletResponse response,
      @ModelAttribute("editCollectedSampleForm") @Valid CollectedSampleBackingForm form,
      BindingResult result, Model model) {

    ModelAndView mv = new ModelAndView("editCollectedSampleForm");
    boolean success = false;
    String message = "";
    Map<String, Object> m = model.asMap();

    // IMPORTANT: Validation code just checks if the ID exists.
    // We still need to store the collected sample as part of the product.
    String donorId = form.getDonorIdHidden();
    String donorNumber = form.getDonorNumber();
    if (donorId != null && !donorId.isEmpty()) {
      try {
        Donor donor = donorRepository.findDonorById(donorId);
        form.setDonor(donor);
      } catch (NoResultException ex) {
        ex.printStackTrace();
        form.setDonor(null);
      } catch (NumberFormatException ex) {
        ex.printStackTrace();
        form.setDonor(null);
      }
    }
    else if (donorNumber != null && !donorNumber.isEmpty()) {
      try {
        Donor donor = donorRepository.findDonorByDonorNumber(donorNumber);
        form.setDonor(donor);
      } catch (NoResultException ex) {
        ex.printStackTrace();
        form.setDonor(null);
      }
    } else {
      form.setDonor(null);
    }

    if (result.hasErrors()) {
      m.put("hasErrors", true);
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);      
      success = false;
      message = "Please fix the errors noted above.";
    } else {
      try {
        CollectedSample collectedSample = form.getCollectedSample();
        collectedSample.setIsDeleted(false);
        collectedSampleRepository.addCollectedSample(collectedSample);
        m.put("hasErrors", false);
        success = true;
        message = "Collection Successfully Added";
        form = new CollectedSampleBackingForm();
        Map<String, Object> formFields = utilController.getFormFieldsForForm("collectedSample");
        m.put("collectedSampleFields", formFields);
        setCollectionNumber(form, (Map<String, Object>) formFields.get("collectionNumber"));
      } catch (EntityExistsException ex) {
        ex.printStackTrace();
        success = false;
        message = "Collection Already exists.";
      } catch (Exception ex) {
        ex.printStackTrace();
        success = false;
        message = "Internal Error. Please try again or report a Problem.";
      }
    }

    m.put("editCollectedSampleForm", form);
    m.put("existingCollectedSample", false);
    m.put("success", success);
    m.put("message", message);
    m.put("refreshUrl", "editCollectionFormGenerator.html");
    m.put("collectedSampleFields", utilController.getFormFieldsForForm("collectedSample"));
    addEditSelectorOptions(m);

    mv.addObject("model", m);
    return mv;
  }

  @RequestMapping(value = "/updateCollectedSample", method = RequestMethod.POST)
  public ModelAndView updateCollectedSample(
      HttpServletResponse response,
      @ModelAttribute("editCollectedSampleForm") @Valid CollectedSampleBackingForm form,
      BindingResult result, Model model) {

    ModelAndView mv = new ModelAndView("editCollectedSampleForm");
    boolean success = false;
    String message = "";
    Map<String, Object> m = model.asMap();
    addEditSelectorOptions(m);
    // only when the collection is correctly added the existingCollectedSample
    // property will be changed
    m.put("existingCollectedSample", true);

    // IMPORTANT: Validation code just checks if the ID exists.
    // We still need to store the collected sample as part of the product.
    String donorNumber = form.getDonorNumber();
    if (donorNumber != null && !donorNumber.isEmpty()) {
      try {
        Donor donor = donorRepository.findDonorByDonorNumber(donorNumber);
        form.setDonor(donor);
      } catch (NoResultException ex) {
        form.setDonor(null);
        ex.printStackTrace();
      }
    } else {
      form.setDonor(null);
    }

    if (result.hasErrors()) {
      m.put("hasErrors", true);
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      success = false;
      message = "Please fix the errors noted above now!";
    }
    else {
      try {

        form.setIsDeleted(false);
        CollectedSample existingCollectedSample = collectedSampleRepository.updateCollectedSample(form.getCollectedSample());
        if (existingCollectedSample == null) {
          m.put("hasErrors", true);
          response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
          success = false;
          m.put("existingCollectedSample", false);
          message = "Collection does not already exist.";
        }
        else {
          m.put("hasErrors", false);
          success = true;
          message = "Collection Successfully Updated";
        }
      } catch (EntityExistsException ex) {
        ex.printStackTrace();
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        success = false;
        message = "Collection Already exists.";
      } catch (Exception ex) {
        ex.printStackTrace();
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        success = false;
        message = "Internal Error. Please try again or report a Problem.";
      }
   }

    m.put("editCollectedSampleForm", form);
    m.put("success", success);
    m.put("message", message);
    m.put("collectedSampleFields", utilController.getFormFieldsForForm("collectedSample"));

    mv.addObject("model", m);

    return mv;
  }

  public static List<CollectedSampleViewModel> getCollectionViewModels(
      List<CollectedSample> collections) {
    if (collections == null)
      return Arrays.asList(new CollectedSampleViewModel[0]);
    List<CollectedSampleViewModel> collectionViewModels = new ArrayList<CollectedSampleViewModel>();
    for (CollectedSample collection : collections) {
      collectionViewModels.add(new CollectedSampleViewModel(collection));
    }
    return collectionViewModels;
  }

  @RequestMapping(value = "/deleteCollectedSample", method = RequestMethod.POST)
  public @ResponseBody
  Map<String, ? extends Object> deleteCollection(
      @RequestParam("collectedSampleId") Long collectionSampleId) {

    boolean success = true;
    String errMsg = "";
    try {
      collectedSampleRepository.deleteCollectedSample(collectionSampleId);
    } catch (Exception ex) {
      // TODO: Replace with logger
      System.err.println("Internal Exception");
      System.err.println(ex.getMessage());
      success = false;
      errMsg = "Internal Server Error";
    }

    Map<String, Object> m = new HashMap<String, Object>();
    m.put("success", success);
    m.put("errMsg", errMsg);
    return m;
  }

  @RequestMapping(value = "/collectionSummary", method = RequestMethod.GET)
  public ModelAndView collectionSummaryGenerator(HttpServletRequest request, Model model,
      @RequestParam(value = "collectionId", required = false) Long collectedSampleId) {

    ModelAndView mv = new ModelAndView("collectionSummary");
    Map<String, Object> m = model.asMap();

    m.put("requestUrl", getUrl(request));

    CollectedSample collectedSample = null;
    if (collectedSampleId != null) {
      collectedSample = collectedSampleRepository.findCollectedSampleById(collectedSampleId);
      if (collectedSample != null) {
        m.put("existingCollectedSample", true);
      }
      else {
        m.put("existingCollectedSample", false);
      }
    }

    CollectedSampleViewModel collectionViewModel = getCollectionViewModels(Arrays.asList(collectedSample)).get(0);
    utilController.addTipsToModel(model.asMap(), "collections.findcollection.collectionsummary");
    m.put("collectedSample", collectionViewModel);
    m.put("refreshUrl", getUrl(request));
    // to ensure custom field names are displayed in the form
    m.put("collectedSampleFields", utilController.getFormFieldsForForm("collectedSample"));
    mv.addObject("model", m);
    return mv;
  }

  @RequestMapping(value="/saveAsWorksheet", method = RequestMethod.GET)
  public ModelAndView saveAsWorksheet(HttpServletRequest request,
      HttpServletResponse response,
      @ModelAttribute("findCollectedSampleForm") CollectionsWorksheetForm form,
      BindingResult result, Model model) {

    String collectionNumber = form.getCollectionNumber();
    if (collectionNumber != null)
      collectionNumber = collectionNumber.trim();
    String dateCollectedFrom = form.getDateCollectedFrom();
    String dateCollectedTo = form.getDateCollectedTo();

    List<Integer> bloodBagTypeIds = new ArrayList<Integer>();
    if (form.getBloodBagTypes() != null) {
      for (String bloodBagTypeId : form.getBloodBagTypes()) {
        bloodBagTypeIds.add(Integer.parseInt(bloodBagTypeId));
      }
    }

    List<Long> centerIds = new ArrayList<Long>();
    if (form.getCollectionCenters() != null) {
      for (String center : form.getCollectionCenters()) {
        centerIds.add(Long.parseLong(center));
      }
    }

    List<Long> siteIds = new ArrayList<Long>();
    if (form.getCollectionSites() != null) {
      for (String site : form.getCollectionSites()) {
        siteIds.add(Long.parseLong(site));
      }
    }

    String worksheetBatchId = form.getWorksheetBatchId();
    ModelAndView mv = new ModelAndView("worksheetSaved");
    Map<String, Object> m = model.asMap();
    m.put("worksheetBatchId", worksheetBatchId);
    try {
      collectedSampleRepository.saveAsWorksheet(
                                        form.getCollectionNumber(),
                                        bloodBagTypeIds, centerIds, siteIds,
                                        dateCollectedFrom, dateCollectedTo,
                                        worksheetBatchId);
      m.put("success", true);
    } catch (Exception ex) {
      ex.printStackTrace();
      m.put("success", false);
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    mv.addObject("model", m);
    
    return mv;
  }

  @RequestMapping(value="/findCollectionWorksheetFormGenerator", method=RequestMethod.GET)
  public ModelAndView findCollectionWorksheetFormGenerator(HttpServletRequest request, Model model) {
    ModelAndView mv = new ModelAndView("findCollectionWorksheetForm");
    Map<String, Object> m = model.asMap();
    m.put("refreshUrl", getUrl(request));
    mv.addObject("model", m);
    return mv;
  }

  @RequestMapping(value="/findCollectionsWorksheet", method=RequestMethod.GET)
  public ModelAndView findCollectionsWorksheet(HttpServletRequest request, Model model,
      @RequestParam(value="worksheetBatchId") String worksheetBatchId) {

    List<CollectedSample> collectedSamples = collectedSampleRepository.findCollectionsInWorksheet(worksheetBatchId);
    ModelAndView mv = new ModelAndView("collectionsWorksheet");

    Map<String, Object> m = new HashMap<String, Object>();
    if (collectedSamples == null) {
      m.put("worksheetFound", false);
    } else {
      m.put("worksheetFound", true);
      m.put("allCollectedSamples", getCollectionViewModels(collectedSamples));
      m.put("bloodTests", bloodTestRepository.getAllBloodTests());
      List<String> propertyOwners = Arrays.asList(ConfigPropertyConstants.COLLECTIONS_WORKSHEET);
      m.put("worksheetConfig", genericConfigRepository.getConfigProperties(propertyOwners));
    }

    m.put("worksheetBatchId", worksheetBatchId);
    mv.addObject("model", m);

    return mv;
  }

}
