package com.lishuang.community.community.dto;

import com.lishuang.community.community.model.Information;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页
 */
@Data
public class PaginationDTO {
    private List<Information> informationList;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showLastPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    public void setPagination(Integer totalCount, Integer page, Integer size) {
        if(totalCount%size == 0){
            totalPage = totalCount/size;
        } else{
            totalPage = totalCount/size+1;
        }
        if(page<1){
            page = 1;
        }
        if(page>totalPage){
            page = totalPage;
        }
        this.page = page;
        pages.add(page);//首先加入第一页
        for(int i=1;i<=3;i++){
            if(page-i>0){
                pages.add(0,page-i); //头插法，保证插入到page的前面
            }
            if(page+i <= totalPage){
                pages.add(page+i);
            }
        }
        //是否展示是上一页
        if(page == 1){
            showPrevious = false;
            //showPrevious = false;
        }else {
            showPrevious = true;
        }

        //是否展示下一页
        if(page == totalPage){
            showNext = false;
        }else{
            showNext = true;
        }

        //是否展示第一页
        if(pages.contains(1)){
            showFirstPage = false;
        }else {
            showFirstPage = true;
        }

        //是否展示最后一页
        if(pages.contains(totalPage)){
            showLastPage = false;
        }else {
            showLastPage = true;
        }

    }
}
