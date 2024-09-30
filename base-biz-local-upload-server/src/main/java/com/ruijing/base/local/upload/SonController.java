package com.ruijing.base.local.upload;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: WangJieLong
 * @Date: 2024-09-26
 */
public class SonController {
    
    
    public static void main(String[] args) throws IOException {
        Path data = Paths.get("C:\\Users\\yond\\Desktop\\dataModify\\zunyi\\0925\\现网遵医管理部门.xlsx");
        Path lib = Paths.get("C:\\Users\\yond\\Desktop\\dataModify\\zunyi\\0925\\【遵医附院】新旧科室一对多的情况.xlsx");
        List<DataDept> dataList = new ArrayList<>();
        getData(dataList, data, DataDept.class);
        List<LibDept> libList = new ArrayList<>();
        getData(libList, lib, LibDept.class);
        
        Map<String, DataDept> dataNameMap = dataList.stream().collect(Collectors.toMap(DataDept::getName, Function.identity()));
        Map<String, DataDept> dataCodeMap = dataList.stream().collect(Collectors.toMap(DataDept::getCode, Function.identity()));
        
        
        Map<String, LibDept> libNameMap = libList.stream().collect(Collectors.toMap(LibDept::getOldName, Function.identity(), (key1, key2) -> key1));
        Map<String, LibDept> libCodeMap = libList.stream().collect(Collectors.toMap(LibDept::getOldCode, Function.identity(), (key1, key2) -> key1));
        
        for (DataDept dept : dataList) {
            LibDept libExist = libNameMap.get(dept.getName());
            if (libExist == null) {
                libExist = libCodeMap.get(dept.getCode());
                if (libExist == null) {
                    continue;
                }
            }
            dept.setOldCode(libExist.getOldCode());
            dept.setOldName(libExist.getOldName());
            dept.setNewCode(libExist.getNewCode());
            dept.setNewName(libExist.getNewName());
            DataDept exist = dataNameMap.get(libExist.getNewName());
            if (exist == null) {
                exist = dataCodeMap.get(libExist.getNewCode());
            }
            if (exist == null) {
                dept.setInfo("新科室未创建");
            } else {
                dept.setInfo(exist.getName() + exist.getCode() + "，部门信息已存在，直接更新编码");
            }
        }
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        EasyExcel.write(out, DataDept.class)
                .sheet("匹配信息")
                .doWrite(() -> {
                    return dataList;
                });
        Path process = Paths.get("C:\\Users\\yond\\Desktop\\dataModify\\zunyi\\0925\\匹配.xlsx");
        Files.write(process, out.toByteArray());
    }
    
    
    private static <T> List<T> getData(List<T> list, Path path, Class<T> tClass) throws IOException {
        
        EasyExcel.read(Files.newInputStream(path), tClass, new ReadListener<T>() {
            
            @Override
            public void invoke(T data, AnalysisContext context) {
                list.add(data);
            }
            
            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
            
            }
        }).ignoreEmptyRow(true).sheet().doRead();
        return list;
    }
    
    public static class DataDept {
        @ExcelProperty(index = 0, value = "锐竞部门id")
        private String id;
        @ExcelProperty(index = 1, value = "锐竞部门名称")
        private String name;
        @ExcelProperty(index = 2, value = "锐竞部门编码")
        private String code;
        
        @ExcelProperty(index = 3, value = "匹配老科室编码")
        private String oldCode;
        @ExcelProperty(index = 4, value = "匹配老科室名称")
        private String oldName;
        @ExcelProperty(index = 5, value = "新科室编码")
        private String newCode;
        @ExcelProperty(index = 6, value = "新科室名称")
        private String newName;
        @ExcelProperty(index = 7, value = "匹配信息")
        private String info;
        
        public String getCode() {
            return code;
        }
        
        public void setCode(String code) {
            this.code = code;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public String getId() {
            return id;
        }
        
        public void setId(String id) {
            this.id = id;
        }
        
        public String getOldCode() {
            return oldCode;
        }
        
        public void setOldCode(String oldCode) {
            this.oldCode = oldCode;
        }
        
        public String getOldName() {
            return oldName;
        }
        
        public void setOldName(String oldName) {
            this.oldName = oldName;
        }
        
        public String getNewCode() {
            return newCode;
        }
        
        public void setNewCode(String newCode) {
            this.newCode = newCode;
        }
        
        public String getNewName() {
            return newName;
        }
        
        public void setNewName(String newName) {
            this.newName = newName;
        }
        
        public String getInfo() {
            return info;
        }
        
        public void setInfo(String info) {
            this.info = info;
        }
    }
    
    
    public static class LibDept {
        @ExcelProperty(index = 1)
        private String oldCode;
        @ExcelProperty(index = 2)
        private String oldName;
        @ExcelProperty(index = 3)
        private String newCode;
        @ExcelProperty(index = 4)
        private String newName;
        
        public String getOldCode() {
            return oldCode;
        }
        
        public void setOldCode(String oldCode) {
            this.oldCode = oldCode;
        }
        
        public String getOldName() {
            return oldName;
        }
        
        public void setOldName(String oldName) {
            this.oldName = oldName;
        }
        
        public String getNewCode() {
            return newCode;
        }
        
        public void setNewCode(String newCode) {
            this.newCode = newCode;
        }
        
        public String getNewName() {
            return newName;
        }
        
        public void setNewName(String newName) {
            this.newName = newName;
        }
    }
}
