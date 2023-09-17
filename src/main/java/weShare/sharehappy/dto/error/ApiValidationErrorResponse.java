package weShare.sharehappy.dto.error;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiValidationErrorResponse {
    private List<FieldErrorInfo> fieldErrorInfos = new ArrayList<>();
    private List<RejectValueInfo> rejectValueInfos = new ArrayList<>();
    private List<String> globalErrorInfos = new ArrayList<>();

    public void addFieldErrorInfo(FieldErrorInfo fieldErrorInfo){
        fieldErrorInfos.add(fieldErrorInfo);
    }

    public void addRejectValueInfo(RejectValueInfo rejectValueInfo){
        rejectValueInfos.add(rejectValueInfo);
    }

    public void addGlobalErrorInfo(String globalErrorInfo){
        globalErrorInfos.add(globalErrorInfo);
    }

}
