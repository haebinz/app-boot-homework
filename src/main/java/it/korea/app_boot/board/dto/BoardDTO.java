package it.korea.app_boot.board.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import it.korea.app_boot.board.entity.BoardEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BoardDTO {


    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Response {
       	private int brdId;
		private String title;
		private String writer;
		private int readCount;
		private int likeCount;
        private LocalDateTime createDate;
		private LocalDateTime updateDate;
        

        public static Response of (BoardEntity entity) {
           
            return Response.builder()
                   .brdId(entity.getBrdId())
                   .title(entity.getTitle())
                   .writer(entity.getWriter())
                   .readCount(entity.getReadCount())
                   .likeCount(entity.getLikeCount())
                   .createDate(entity.getCreateDate())
                   .updateDate(entity.getUpdateDate())
                   .build();

        }

        //마지막 수정시간 
        public LocalDateTime getModifiedDate(){
            return this.updateDate == null ? this.createDate : this.updateDate;
        }
    }


     @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Detail {
       	private int brdId;
		private String title;
		private String writer;
        private String contents;
		private int readCount;
		private int likeCount;
        private LocalDateTime createDate;
		private LocalDateTime updateDate;
        private List<BoardFileDTO> fileList;
        
        public static Detail of (BoardEntity entity) {
            //파일 리스트 객체 변환 
            List<BoardFileDTO> fileList = 
                    entity.getFileList().stream()
                        .map(BoardFileDTO::of).toList();
           
            return Detail.builder()
                   .brdId(entity.getBrdId())
                   .title(entity.getTitle())
                   .writer(entity.getWriter())
                   .readCount(entity.getReadCount())
                   .likeCount(entity.getLikeCount())
                   .contents(entity.getContents())
                   .fileList(fileList)
                   .createDate(entity.getCreateDate())
                   .updateDate(entity.getUpdateDate())
                   .build();
        }

        //마지막 수정시간 
        public LocalDateTime getModifiedDate(){
            return this.updateDate == null ? this.createDate : this.updateDate;
        }
    }


    @Data
	public static class Request{
		private int brdId;

        @NotBlank(message = "제목은 필수 항목입니다.")
		private String title;
        @NotBlank(message = "내용은 필수 항목입니다.")
		private String contents;
		private String writer;
		//첨부파일 
		private MultipartFile file;
	}
	

}
