package com.angkorteam.fintech;

import com.angkorteam.fintech.client.FineractClient;
import com.angkorteam.fintech.client.dto.AuthenticateRequest;
import com.angkorteam.fintech.client.dto.PostAuthenticationResponse;
import com.angkorteam.fintech.client.dto.PostDataTableRegisterRequest;
import com.angkorteam.fintech.client.dto.PostDataTableRequest;
import com.angkorteam.fintech.client.enums.TableTypeEnum;
import com.angkorteam.fintech.client.renums.DataTableCategoryEnum;

import java.text.ParseException;

public class TestProgram {


    public static void main(String[] args) throws ParseException {
        String mifosUrl = "https://bank-api.i365work.com:21632/fineract-provider/api/v1";

        String tenant = "default";

        FineractClient client = new FineractClient(mifosUrl);

        PostAuthenticationResponse response = client.authentication(tenant, new AuthenticateRequest("mifos", "password"));

        String token = response.getBase64EncodedAuthenticationKey();

//        PostDataTableRequest request = new PostDataTableRequest();
//        request.setMultiRow(true);
//        request.setApptableName(TableTypeEnum.Office);
//        request.setDatatableName("ppppppppppppppppp");
//        request.getColumns().add(PostDataTableRequest.Column.string("aaaaaaaaa", true, 100));
//        client.datatableCreate(tenant, token, request);

//        client.datatableDeregister(tenant, token, "ppppppppppppppppp");

        PostDataTableRegisterRequest request1 = new PostDataTableRegisterRequest();
        request1.setCategory(DataTableCategoryEnum.DEFAULT);
        client.datatableRegister(tenant, token, "ppppppppppppppppp", TableTypeEnum.Office, request1);
//        client.datatableDelete(tenant, token, "sssssssssss");
//        client.datatableDeregister(tenant, token, "sssssssssss");

        // client.datatableDelete(tenant, token, "vvv");

    }
}
