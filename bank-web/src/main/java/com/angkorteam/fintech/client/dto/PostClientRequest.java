package com.angkorteam.fintech.client.dto;

import com.angkorteam.fintech.client.enums.LegalForm;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PostClientRequest {

    @SerializedName("isStaff")
    private boolean staff;

    private long officeId;

    private long groupId;

    private long staffId;

    private long genderId;

    private long clientTypeId;

    private long clientClassificationId;

    @SerializedName("savingsProductId")
    private long savingProductId;

    @SerializedName("legalFormId")
    private LegalForm legalForm;

    private String accountNo;

    private String externalId;

    private String mobileNo;

    private String emailAddress;

    @SerializedName("firstname")
    private String firstName;

    @SerializedName("middlename")
    private String middleName;

    @SerializedName("lastname")
    private String lastName;

    @SerializedName("fullname")
    private String fullName;

    private Date dateOfBirth;

    private boolean active;

    private Date activationDate;

    private Date submittedOnDate;

    @SerializedName("clientNonPersonDetails")
    private ClientNonPersonDetail clientNonPersonDetail;

    @SerializedName("address")
    private List<Address> address = new ArrayList<>();

    private List<FamilyMember> familyMembers = new ArrayList<>();

    private List<DataTable> datatables = new ArrayList<>();

    public List<FamilyMember> getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(List<FamilyMember> familyMembers) {
        this.familyMembers = familyMembers;
    }

    public List<DataTable> getDatatables() {
        return datatables;
    }

    public void setDatatables(List<DataTable> datatables) {
        this.datatables = datatables;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getSubmittedOnDate() {
        return submittedOnDate;
    }

    public void setSubmittedOnDate(Date submittedOnDate) {
        this.submittedOnDate = submittedOnDate;
    }

    public long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(long officeId) {
        this.officeId = officeId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(Date activationDate) {
        this.activationDate = activationDate;
    }

    public boolean isStaff() {
        return staff;
    }

    public void setStaff(boolean staff) {
        this.staff = staff;
    }

    public long getStaffId() {
        return staffId;
    }

    public void setStaffId(long staffId) {
        this.staffId = staffId;
    }

    public long getGenderId() {
        return genderId;
    }

    public void setGenderId(long genderId) {
        this.genderId = genderId;
    }

    public long getClientTypeId() {
        return clientTypeId;
    }

    public void setClientTypeId(long clientTypeId) {
        this.clientTypeId = clientTypeId;
    }

    public long getClientClassificationId() {
        return clientClassificationId;
    }

    public void setClientClassificationId(long clientClassificationId) {
        this.clientClassificationId = clientClassificationId;
    }

    public long getSavingProductId() {
        return savingProductId;
    }

    public void setSavingProductId(long savingProductId) {
        this.savingProductId = savingProductId;
    }

    public LegalForm getLegalForm() {
        return legalForm;
    }

    public void setLegalForm(LegalForm legalForm) {
        this.legalForm = legalForm;
    }

    public ClientNonPersonDetail getClientNonPersonDetail() {
        return clientNonPersonDetail;
    }

    public void setClientNonPersonDetail(ClientNonPersonDetail clientNonPersonDetail) {
        this.clientNonPersonDetail = clientNonPersonDetail;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public static class ClientNonPersonDetail {

        private String incorpNumber;

        @SerializedName("remarks")
        private String remarks;

        private Date incorpValidityTillDate;

        private long constitutionId;

        private long mainBusinessLineId;

        public ClientNonPersonDetail() {
        }

        public ClientNonPersonDetail(String incorpNumber, String remarks, Date incorpValidityTillDate, long constitutionId, long mainBusinessLineId) {
            this.incorpNumber = incorpNumber;
            this.remarks = remarks;
            this.incorpValidityTillDate = incorpValidityTillDate;
            this.constitutionId = constitutionId;
            this.mainBusinessLineId = mainBusinessLineId;
        }

        public String getIncorpNumber() {
            return incorpNumber;
        }

        public void setIncorpNumber(String incorpNumber) {
            this.incorpNumber = incorpNumber;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public Date getIncorpValidityTillDate() {
            return incorpValidityTillDate;
        }

        public void setIncorpValidityTillDate(Date incorpValidityTillDate) {
            this.incorpValidityTillDate = incorpValidityTillDate;
        }

        public long getConstitutionId() {
            return constitutionId;
        }

        public void setConstitutionId(long constitutionId) {
            this.constitutionId = constitutionId;
        }

        public long getMainBusinessLineId() {
            return mainBusinessLineId;
        }

        public void setMainBusinessLineId(long mainBusinessLineId) {
            this.mainBusinessLineId = mainBusinessLineId;
        }

    }

    public static class Address {

        private long stateProvinceId;

        private long countryId;

        private long addressTypeId;

        @SerializedName("isActive")
        private boolean active;

        private String street;

        private String addressLine1;

        private String addressLine2;

        private String addressLine3;

        private String townVillage;

        private String city;

        private String countyDistrict;

        private String postalCode;

        private double latitude;

        private double longitude;

        private String createdBy;

        /**
         * yyyy-MM-dd
         */
        private Date createdOn;

        private String updatedBy;

        /**
         * yyyy-MM-dd
         */
        private Date updatedOn;

        public long getStateProvinceId() {
            return stateProvinceId;
        }

        public void setStateProvinceId(long stateProvinceId) {
            this.stateProvinceId = stateProvinceId;
        }

        public long getCountryId() {
            return countryId;
        }

        public void setCountryId(long countryId) {
            this.countryId = countryId;
        }

        public long getAddressTypeId() {
            return addressTypeId;
        }

        public void setAddressTypeId(long addressTypeId) {
            this.addressTypeId = addressTypeId;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getAddressLine1() {
            return addressLine1;
        }

        public void setAddressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
        }

        public String getAddressLine2() {
            return addressLine2;
        }

        public void setAddressLine2(String addressLine2) {
            this.addressLine2 = addressLine2;
        }

        public String getAddressLine3() {
            return addressLine3;
        }

        public void setAddressLine3(String addressLine3) {
            this.addressLine3 = addressLine3;
        }

        public String getTownVillage() {
            return townVillage;
        }

        public void setTownVillage(String townVillage) {
            this.townVillage = townVillage;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountyDistrict() {
            return countyDistrict;
        }

        public void setCountyDistrict(String countyDistrict) {
            this.countyDistrict = countyDistrict;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public Date getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(Date createdOn) {
            this.createdOn = createdOn;
        }

        public String getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
        }

        public Date getUpdatedOn() {
            return updatedOn;
        }

        public void setUpdatedOn(Date updatedOn) {
            this.updatedOn = updatedOn;
        }

    }

    public static class FamilyMember {

        private String firstName;

        private String middleName;

        private String lastName;

        private String qualification;

        private String mobileNumber;

        private long age;

        private boolean dependent;

        private long relationshipId;

        private long maritalStatusId;

        private long genderId;

        private long professionId;

        private Date dateOfBirth;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getMiddleName() {
            return middleName;
        }

        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getQualification() {
            return qualification;
        }

        public void setQualification(String qualification) {
            this.qualification = qualification;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public long getAge() {
            return age;
        }

        public void setAge(long age) {
            this.age = age;
        }

        public boolean isDependent() {
            return dependent;
        }

        public void setDependent(boolean dependent) {
            this.dependent = dependent;
        }

        public long getRelationshipId() {
            return relationshipId;
        }

        public void setRelationshipId(long relationshipId) {
            this.relationshipId = relationshipId;
        }

        public long getMaritalStatusId() {
            return maritalStatusId;
        }

        public void setMaritalStatusId(long maritalStatusId) {
            this.maritalStatusId = maritalStatusId;
        }

        public long getGenderId() {
            return genderId;
        }

        public void setGenderId(long genderId) {
            this.genderId = genderId;
        }

        public long getProfessionId() {
            return professionId;
        }

        public void setProfessionId(long professionId) {
            this.professionId = professionId;
        }

        public Date getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(Date dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

    }



}
