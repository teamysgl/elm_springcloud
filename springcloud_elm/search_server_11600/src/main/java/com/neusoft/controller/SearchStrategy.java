package com.neusoft.controller;

import java.util.List;

public interface SearchStrategy<T> {
    List<T> search(String keyword) throws Exception;
}

