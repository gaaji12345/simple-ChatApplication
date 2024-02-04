package dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransferData {
    private String msg;
    private String from;
    private String  to;
    private String command;
}
