package com.library.citadel_library.exception.message;

public class ErrorMessage {

    public final static String LOAN_TIME_INCORRECT_MESSAGE ="Loan pick up time or drop off time not correct";
    public final static String USER_NOT_FOUND_MESSAGE="User with email %s not found";
    public final static String EMAIL_ALREADY_EXIST_MESSAGE="Email already exist: %s";
    public final static String ROLE_NOT_FOUND_MESSAGE="Role with name %s not found";
    public final static String BOOK_NOT_FOUND_MESSAGE="Book with id %d not found";
    public final static String BOOK_NOT_AVAILABLE_TO_REMOVE_MESSAGE="Book with id %d not available to remove";
    public final static String INVALID_BOOK_PARAMETER_MESSAGE="Please write valid parameter";
    public final static String LOAN_NOT_FOUND_MESSAGE="Loan with id %d not found";


    /* CATEGORY EXCEPTION MESSAGES */
    public final static String CATEGORY_NOT_FOUND_MESSAGE="Category with id %d not found";
    public final static String CATEGORY_NOT_DELETE_MESSAGE="Category has book category id %d ";

    /* PUBLISHER EXCEPTION MESSAGES */
    public final static String PUBLISHER_NOT_FOUND_MESSAGE="Publisher with id %d not found";
    public final static String PUBLISHER_NOT_DELETE_MESSAGE="Publisher has book publisher id %d";

    /* AUTHOR EXCEPTION MESSAGES */
    public final static String AUTHOR_NOT_FOUND_MESSAGE="Author with id %d not found";
    public final static String AUTHOR_NOT_DELETE_MESSAGE="Author has book Author id %d";

}
