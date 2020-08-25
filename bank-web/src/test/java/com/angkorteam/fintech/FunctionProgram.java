package com.angkorteam.fintech;

import com.angkorteam.fintech.client.FineractClient;
import com.angkorteam.fintech.client.dto.*;
import com.angkorteam.fintech.client.enums.LegalForm;
import com.angkorteam.fintech.client.enums.TableTypeEnum;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.UUID;

public class FunctionProgram {

    protected static FineractResponse createGroup(FineractClient client, String tenant, String token) {
        PostGroupRequest request = new PostGroupRequest();
        request.setName(RandomStringUtils.randomAlphabetic(50));
        request.setExternalId(UUID.randomUUID().toString());
        request.setCenterId(22);
        request.setOfficeId(2);
        request.setStaffId(261);
        request.setActive(true);
        request.setActivationDate(new Date());

//        request.getClientMembers()
        request.setSubmittedOnDate(new Date());

        DataTable dataTable = new DataTable();
        dataTable.setRegisteredTableName("group_nlurgsvndl");
        dataTable.getData().put("datexoifb", DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
        request.getDataTables().add(dataTable);

        return client.groupCreate(tenant, token, request);
    }

    protected static FineractResponse createCenter(FineractClient client, String tenant, String token) {
        PostCenterRequest request = new PostCenterRequest();
        request.setOfficeId(2);
        request.setName(RandomStringUtils.randomAlphabetic(100));
        request.setActive(true);
        request.setExternalId(UUID.randomUUID().toString());
        request.setActivationDate(new Date());
        request.setStaffId(261);
        request.setSubmittedOnDate(new Date());
        // request.getGroupMembers().add(...);
//        DataTable dataTable = new DataTable();
//        dataTable.setRegisteredTableName("center_ikavvnuecf");
//        dataTable.getData().put("dateqieux", DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
//        request.getDataTables().add(dataTable);

        return client.centerCreate(tenant, token, request);
//
    }

    protected static FineractResponse createClient(FineractClient client, String tenant, String token) {
        PostClientRequest request = new PostClientRequest();
//        request.setStaff(true);
        request.setOfficeId(1);
        request.setGroupId(0);
        request.setStaffId(0);
        request.setGenderId(0);
        request.setClientTypeId(0);
        request.setClientClassificationId(0);
        request.setSavingProductId(0);
        request.setLegalForm(LegalForm.Person);
        request.setAccountNo(RandomStringUtils.randomAlphabetic(20));
        request.setExternalId(RandomStringUtils.randomAlphabetic(20));
        request.setMobileNo(UUID.randomUUID().toString());
        request.setEmailAddress(UUID.randomUUID().toString());
        request.setFirstName(UUID.randomUUID().toString());
        request.setMiddleName(UUID.randomUUID().toString());
        request.setLastName(UUID.randomUUID().toString());
        request.setDateOfBirth(new Date());
        request.setActive(true);
        request.setActivationDate(new Date());
        request.setSubmittedOnDate(new Date());

        PostClientRequest.ClientNonPersonDetail clientNonPersonDetail = new PostClientRequest.ClientNonPersonDetail();
        clientNonPersonDetail.setConstitutionId(0);
        clientNonPersonDetail.setIncorpNumber(UUID.randomUUID().toString());
        clientNonPersonDetail.setIncorpValidityTillDate(new Date());
        clientNonPersonDetail.setMainBusinessLineId(0);
        clientNonPersonDetail.setRemarks(UUID.randomUUID().toString());
//        request.setClientNonPersonDetail(clientNonPersonDetail);

        PostClientRequest.Address address = new PostClientRequest.Address();
        address.setStateProvinceId(0);
        address.setCountryId(0);
        address.setAddressTypeId(0);
        address.setActive(true);
        address.setStreet(UUID.randomUUID().toString());
        address.setAddressLine1(UUID.randomUUID().toString());
        address.setAddressLine2(UUID.randomUUID().toString());
        address.setAddressLine3(UUID.randomUUID().toString());
        address.setTownVillage(UUID.randomUUID().toString());
        address.setCity(UUID.randomUUID().toString());
        address.setCountyDistrict(UUID.randomUUID().toString());
        address.setPostalCode(UUID.randomUUID().toString());
        address.setLatitude(0);
        address.setLongitude(0);
        address.setCreatedBy(UUID.randomUUID().toString());
        address.setCreatedOn(new Date());
        address.setUpdatedBy(UUID.randomUUID().toString());
        address.setUpdatedOn(new Date());
//        request.getAddress().add(address);

        PostClientRequest.FamilyMember familyMember = new PostClientRequest.FamilyMember();
        familyMember.setAge(0);
        familyMember.setDateOfBirth(new Date());
        familyMember.setDependent(true);
        familyMember.setFirstName(UUID.randomUUID().toString());
        familyMember.setGenderId(0);
        familyMember.setLastName(UUID.randomUUID().toString());
        familyMember.setMiddleName(UUID.randomUUID().toString());
        familyMember.setQualification(UUID.randomUUID().toString());
        familyMember.setMobileNumber(UUID.randomUUID().toString());
        familyMember.setRelationshipId(0);
        familyMember.setMaritalStatusId(0);
        familyMember.setProfessionId(0);
        familyMember.setDateOfBirth(new Date());
//        request.getFamilyMembers().add(familyMember);

        DataTable dataTable = new DataTable();
        dataTable.setRegisteredTableName("m_client001");
        dataTable.getData().put("test_date", DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
        request.getDatatables().add(dataTable);

        return client.clientCreate(tenant, token, request);
    }

//    protected static FineractResponse createCenter(FineractClient client, String tenant, String token) {
//        PostCenterRequest request = new PostCenterRequest();
//        request.setActivationDate(LocalDate.now().minusDays(100).toDate());
//        request.setId(1);
//        request.setOfficeId(1);
//        request.setName(RandomStringUtils.randomAlphabetic(50));
//        request.setActive(true);
//        request.setExternalId(RandomStringUtils.randomAlphabetic(50));
//        request.setActivationDate(LocalDate.now().toDate());
//        request.setStaffId(0);
//        request.setSubmittedOnDate(LocalDate.now().toDate());
//
//        // request.getGroupMembers().add(0);
//
//        /**
//         * datatables
//         */
//        private List<DataTable> datatables = new ArrayList<>();
//
//        request.setMultiRow(true);
//        request.setDatatableName(TableTypeEnum.Client.getLiteral() + "001");
//        request.setApptableName(TableTypeEnum.Client);
//        request.getColumns().add(PostDataTableRequest.Column.date("test_date", true));
//        return client.centerCreate(tenant, token, request);
//    }

    protected static FineractResponse createDataTable(FineractClient client, String tenant, String token) {
        PostDataTableRequest request = new PostDataTableRequest();
        request.setMultiRow(true);
        request.setDatatableName("group_" + StringUtils.lowerCase(RandomStringUtils.randomAlphabetic(10)));
        request.setApptableName(TableTypeEnum.Group);
        request.getColumns().add(PostDataTableRequest.Column.date("date" + StringUtils.lowerCase(RandomStringUtils.randomAlphabetic(5)), true));
        return client.datatableCreate(tenant, token, request);
    }

}
