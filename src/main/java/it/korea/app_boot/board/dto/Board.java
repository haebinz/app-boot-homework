package it.korea.app_boot.board.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

public class Board {

	
	@Data
	public static class Request{
		private int brdId;
		private String title;
		private String contents;
		private String writer;
		//첨부파일 
		private MultipartFile file;
	}
	
	
	
	@Data
	public static class Response{
		private int brdId;
		private String title;
		private String writer;
		private int readCount;
		private int likeCount;
		private LocalDateTime modifiedDate;
		
	}
	
	
	@Data
	public static class Detail{	
		private int brdId;
		private String title;
		private String writer;
		private String contents;
		private int readCount;
		private int likeCount;
		private LocalDateTime createDate;
		private LocalDateTime updateDate;
		//첨부파일 리스트 
		List<BoardFiles> files;
		
		//list에 값이 없을 시 null 처리 위해 초기화 
		public List<BoardFiles> getFiles(){
			if(this.files == null) {
				this.files = new ArrayList<>();
			}
			return this.files;
		}
	}
	
	
	
	@Data
	public static class BoardFiles{
		
		private int bfId;
		
		private int brdId;
		
		private String fileName;
		
		private String storedName;
		
		private String filePath;
		
		private long   fileSize;
		
	}
	
}
