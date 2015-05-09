package com.cs.jevents;

public class StacktraceProvider {
    public String getCallerClass(int level) {
        return Thread.currentThread().getStackTrace()[level].getClassName();
    }
}