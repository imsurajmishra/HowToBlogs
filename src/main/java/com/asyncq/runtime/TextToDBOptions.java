package com.asyncq.runtime;

public class TextToDBOptions implements Options{

    String inputFilePath(){
        return "/input/example/user.txt";
    }

    String OutputTableName(){
        return "users";
    }

    String OutputSchemaName(){
        return "public";
    }
}
