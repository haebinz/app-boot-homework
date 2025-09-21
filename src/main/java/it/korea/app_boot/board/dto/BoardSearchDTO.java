package it.korea.app_boot.board.dto;

import lombok.Data;

@Data
public class BoardSearchDTO{

    private int page;
    private int size;
    private String sidx;
    private String sord;
    private String schType;
    private String schText;


    //헤더 정렬 시 정렬 순서 만들어주기 
    public String getOrderStr() {
        String order = "brd_id desc";

        if(sidx != null && sord != null) {
            order = "" ;
            String[] sidxs = sidx.split(",");
            String[] sords = sord.split(",");

            for(int i = 0; i < sidxs.length; i++) {

                if(i == 0) {
                    order +=  getColum( sidxs[i] ) +" " +  sords[i];
                }else {
                    order +=  ", " + getColum( sidxs[i])  +" " +  sords[i];
                }
            }
        }
        return order;
    }


    public int getOffSet(){
        return this.page * this.size;
    }

    public int getCount() {
        return this.size;
    }

    private String getColum(String column) {
      return 
        switch (column) {
            case "brdId" ->  "brd_id";
            case "writer" ->  "writer";
            case "readCount" ->  "read_count";
            case "likeCount" ->  "like_count";
            case "modifiedDate" ->  "modified_date";
            default -> "brd_id";
        };
    }
}