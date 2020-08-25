package com.alodiga.wallet.ejb;

import com.alodiga.wallet.common.ejb.PersonEJB;
import com.alodiga.wallet.common.ejb.PersonEJBLocal;
import com.alodiga.wallet.common.ejb.UtilsEJB;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import org.apache.log4j.Logger;
import com.alodiga.wallet.common.exception.EmptyListException;
import com.alodiga.wallet.common.exception.GeneralException;
import com.alodiga.wallet.common.exception.NullParameterException;
import com.alodiga.wallet.common.exception.RegisterNotFoundException;
import com.alodiga.wallet.common.genericEJB.AbstractWalletEJB;
import com.alodiga.wallet.common.genericEJB.EJBRequest;
import com.alodiga.wallet.common.genericEJB.WalletContextInterceptor;
import com.alodiga.wallet.common.genericEJB.WalletLoggerInterceptor;
import com.alodiga.wallet.common.model.AddressType;
import com.alodiga.wallet.common.model.CivilStatus;
import com.alodiga.wallet.common.model.ComercialAgency;
import com.alodiga.wallet.common.model.DocumentsPersonType;
import com.alodiga.wallet.common.model.EdificationType;
import com.alodiga.wallet.common.model.EmployedPosition;
import com.alodiga.wallet.common.model.Employee;
import com.alodiga.wallet.common.model.LegalPerson;
import com.alodiga.wallet.common.model.LegalRepresentative;
import com.alodiga.wallet.common.model.NaturalPerson;
import com.alodiga.wallet.common.model.PasswordChangeRequest;
import com.alodiga.wallet.common.model.Person;
import com.alodiga.wallet.common.model.PersonClassification;
import com.alodiga.wallet.common.model.PersonHasAddress;
import com.alodiga.wallet.common.model.PersonType;
import com.alodiga.wallet.common.model.PhonePerson;
import com.alodiga.wallet.common.model.PhoneType;
import com.alodiga.wallet.common.model.Profession;
import com.alodiga.wallet.common.model.StatusApplicant;
import com.alodiga.wallet.common.model.StreetType;
import com.alodiga.wallet.common.utils.EjbConstants;
import com.alodiga.wallet.common.utils.QueryConstants;
import java.util.Map;

@Interceptors({WalletLoggerInterceptor.class, WalletContextInterceptor.class})
@Stateless(name = EjbConstants.PERSON_EJB, mappedName = EjbConstants.PERSON_EJB)
@TransactionManagement(TransactionManagementType.BEAN)
public class PersonEJBImp extends AbstractWalletEJB implements PersonEJB, PersonEJBLocal {

    private static final Logger logger = Logger.getLogger(PersonEJBImp.class);

    //DocumentPersonType
    public List<DocumentsPersonType> getDocumentsPersonType(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        return (List<DocumentsPersonType>) listEntities(DocumentsPersonType.class, request, logger, getMethodName());
    }

    public DocumentsPersonType loadDocumentsPersonType(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        DocumentsPersonType documentsPersonType = (DocumentsPersonType) loadEntity(DocumentsPersonType.class, request, logger, getMethodName());
        return documentsPersonType;
    }

    public DocumentsPersonType saveDocumentsPersonType(DocumentsPersonType documentsPersonType) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (documentsPersonType == null) {
            throw new NullParameterException("documentsPersonType", null);
        }
        return (DocumentsPersonType) saveEntity(documentsPersonType);
    }

    //PersonType
    @Override
    public List<PersonType> getPersonTypes(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<PersonType> personTypes = (List<PersonType>) listEntities(PersonType.class, request, logger, getMethodName());
        return personTypes;
    }

    @Override
    public PersonType loadPersonType(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        PersonType personTypes = (PersonType) loadEntity(PersonType.class, request, logger, getMethodName());
        return personTypes;
    }

    @Override
    public PersonType savePersonType(PersonType personType) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (personType == null) {
            throw new NullParameterException("personType", null);
        }
        return (PersonType) saveEntity(personType);
    }

    @Override
    public List<PersonType> getPersonTypeByCountry(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<PersonType> personTypes = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_COUNTRY_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_COUNTRY_ID), null);
        }
//        if (!params.containsKey(EjbConstants.PARAM_ORIGIN_APPLICATION_ID)) {
//            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_ORIGIN_APPLICATION_ID), null);
//        }
        personTypes = (List<PersonType>) getNamedQueryResult(PersonType.class, QueryConstants.PERSON_TYPE_BY_COUNTRY, request, getMethodName(), logger, "personTypes");
        return personTypes;
    }

    //Tabla de PhonePerson
    public List<PhonePerson> getPhonePerson(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        return (List<PhonePerson>) listEntities(PhonePerson.class, request, logger, getMethodName());
    }

    @Override
    public List<PhonePerson> getPhoneByPerson(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<PhonePerson> phonePersonList = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PERSON_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PERSON_ID), null);
        }
        phonePersonList = (List<PhonePerson>) getNamedQueryResult(PhonePerson.class, QueryConstants.PHONES_BY_PERSON, request, getMethodName(), logger, "phonePersonList");
        return phonePersonList;
    }

    public PhonePerson loadPhonePerson(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        PhonePerson phonePerson = (PhonePerson) loadEntity(PhonePerson.class, request, logger, getMethodName());
        return phonePerson;
    }

    public PhonePerson savePhonePerson(PhonePerson phonePerson) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (phonePerson == null) {
            throw new NullParameterException("phonePerson", null);
        }
        return (PhonePerson) saveEntity(phonePerson);
    }

    //PersonHasAddress
    @Override
    public List<PersonHasAddress> getPersonHasAddresses(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        return (List<PersonHasAddress>) listEntities(PersonHasAddress.class, request, logger, getMethodName());
    }

    @Override
    public List<PersonHasAddress> getPersonHasAddressesByPerson(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<PersonHasAddress> personHasAddressByPerson = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_PERSON_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_PERSON_ID), null);
        }
        personHasAddressByPerson = (List<PersonHasAddress>) getNamedQueryResult(UtilsEJB.class, QueryConstants.PERSON_HAS_ADDRESS_BY_PERSON, request, getMethodName(), logger, "personHasAddressByPerson");
        return personHasAddressByPerson;
    }

    @Override
    public PersonHasAddress loadPersonHasAddress(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        PersonHasAddress personHasAddress = (PersonHasAddress) loadEntity(PersonHasAddress.class, request, logger, getMethodName());
        return personHasAddress;
    }

    @Override
    public PersonHasAddress savePersonHasAddress(PersonHasAddress personHasAddress) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (personHasAddress == null) {
            throw new NullParameterException("personHasAddress", null);
        }
        return (PersonHasAddress) saveEntity(personHasAddress);
    }

    //LegalRepresentative
    public List<LegalRepresentative> getLegalRepresentative(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        return (List<LegalRepresentative>) listEntities(LegalRepresentative.class, request, logger, getMethodName());
    }

    public LegalRepresentative loadLegalRepresentative(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        LegalRepresentative legalRepresentative = (LegalRepresentative) loadEntity(LegalRepresentative.class, request, logger, getMethodName());
        return legalRepresentative;
    }

    public LegalRepresentative saveLegalRepresentative(LegalRepresentative legalRepresentative) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (legalRepresentative == null) {
            throw new NullParameterException("legalRepresentative", null);
        }
        return (LegalRepresentative) saveEntity(legalRepresentative);
    }

    //Person
    public List<Person> getPerson(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        return (List<Person>) listEntities(Person.class, request, logger, getMethodName());
    }

    public Person loadPerson(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Person person = (Person) loadEntity(Person.class, request, logger, getMethodName());
        return person;
    }

    public Person savePerson(Person person) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (person == null) {
            throw new NullParameterException("person", null);
        }
        return (Person) saveEntity(person);
    }

    //Natural Person
    public List<NaturalPerson> getNaturalPerson(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        return (List<NaturalPerson>) listEntities(NaturalPerson.class, request, logger, getMethodName());
    }

    public NaturalPerson loadNaturalPerson(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        NaturalPerson naturalPerson = (NaturalPerson) loadEntity(NaturalPerson.class, request, logger, getMethodName());
        return naturalPerson;
    }

    public NaturalPerson saveNaturalPerson(NaturalPerson naturalPerson) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (naturalPerson == null) {
            throw new NullParameterException("naturalPerson", null);
        }
        return (NaturalPerson) saveEntity(naturalPerson);
    }

    //Legal Person
    public List<LegalPerson> getLegalPerson(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        return (List<LegalPerson>) listEntities(LegalPerson.class, request, logger, getMethodName());
    }
    
    public List<LegalPerson> getLegalPersonByLegalRepresentative(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException{
        List<LegalPerson> legalPersonByRepresentative = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(EjbConstants.PARAM_LEGAL_REPRESENTATIVE_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), EjbConstants.PARAM_LEGAL_REPRESENTATIVE_ID), null);
        }
        legalPersonByRepresentative = (List<LegalPerson>) getNamedQueryResult(LegalPerson.class, QueryConstants.LEGAL_PERSON_BY_LEGAL_REPRESENTATIVE, request, getMethodName(), logger, "legalPersonByRepresentative");
        return legalPersonByRepresentative;
    }

    public LegalPerson loadLegalPerson(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        LegalPerson legalPerson = (LegalPerson) loadEntity(LegalPerson.class, request, logger, getMethodName());
        return legalPerson;
    }

    public LegalPerson saveLegalPerson(LegalPerson legalPerson) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (legalPerson == null) {
            throw new NullParameterException("legalPerson", null);
        }
        return (LegalPerson) saveEntity(legalPerson);
    }

    //StatusApplicant
    public List<StatusApplicant> getStatusApplicant(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        return (List<StatusApplicant>) listEntities(StatusApplicant.class, request, logger, getMethodName());
    }

    public StatusApplicant loadStatusApplicant(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        StatusApplicant statusApplicant = (StatusApplicant) loadEntity(StatusApplicant.class, request, logger, getMethodName());
        return statusApplicant;
    }

    public StatusApplicant saveStatusApplicant(StatusApplicant statusApplicant) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (statusApplicant == null) {
            throw new NullParameterException("statusApplicant", null);
        }
        return (StatusApplicant) saveEntity(statusApplicant);
    }

    
    //PasswordChangeRequest
    public List<PasswordChangeRequest> getPasswordChangeRequest(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException{
        return (List<PasswordChangeRequest>) listEntities(PasswordChangeRequest.class, request, logger, getMethodName());
    }
    
    public PasswordChangeRequest loadPasswordChangeRequest(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException{
        PasswordChangeRequest passwordChangeRequest = (PasswordChangeRequest) loadEntity(PasswordChangeRequest.class, request, logger, getMethodName());
        return passwordChangeRequest;
    }
    
    public PasswordChangeRequest savePasswordChangeRequest(PasswordChangeRequest passwordChangeRequest) throws RegisterNotFoundException, NullParameterException, GeneralException{
        if (passwordChangeRequest == null) {
            throw new NullParameterException("passwordChangeRequest", null);
        }
        return (PasswordChangeRequest) saveEntity(passwordChangeRequest);
    }

    @Override
    public List<Employee> getEmployee(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Employee saveEmployee(Employee employee) throws RegisterNotFoundException, NullParameterException, GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PersonClassification loadPersonClassification(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DocumentsPersonType> getDocumentsPersonByCountry(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PhonePerson> getValidateMainPhone(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ComercialAgency> getComercialAgency(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<EmployedPosition> getEmployedPosition(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CivilStatus loadCivilStatus(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CivilStatus> getCivilStatus(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CivilStatus saveCivilStatus(CivilStatus civilStatus) throws RegisterNotFoundException, NullParameterException, GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Profession loadProfession(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Profession> getProfession(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Profession saveProfession(Profession profession) throws RegisterNotFoundException, NullParameterException, GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PhoneType loadPhoneType(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PhoneType> getPhoneType(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PhoneType savePhoneType(PhoneType phoneType) throws RegisterNotFoundException, NullParameterException, GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AddressType loadAddressType(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<AddressType> getAddressType(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AddressType saveAddressType(AddressType addressType) throws RegisterNotFoundException, NullParameterException, GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EdificationType loadEdificationType(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<EdificationType> getEdificationType(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EdificationType saveEdificationType(EdificationType edificationType) throws RegisterNotFoundException, NullParameterException, GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public StreetType loadStreetType(EJBRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<StreetType> getStreetType(EJBRequest request) throws EmptyListException, GeneralException, NullParameterException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public StreetType saveStreetType(StreetType streetType) throws RegisterNotFoundException, NullParameterException, GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
