package com.library.citadel_library.controller;

import com.library.citadel_library.dto.LoanDTO;
import com.library.citadel_library.dto.requests.LoanUpdateRequest;
import com.library.citadel_library.dto.response.LoanResponse;
import com.library.citadel_library.dto.response.LoanResponseBook;
import com.library.citadel_library.dto.response.LoanResponseBookUser;
import com.library.citadel_library.dto.response.LoanUpdateResponse;
import com.library.citadel_library.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/loans")
@AllArgsConstructor
public class LoanController {

    private LoanService loanService;

    @PostMapping("/add")
    public ResponseEntity<LoanDTO> createLoan(@Valid @RequestBody LoanDTO loanDTO){

        LoanDTO loan =loanService.createLoan(loanDTO);

        return new ResponseEntity<>(loan, HttpStatus.CREATED);
    }

    //It will return own loans of authenticated user
    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping()
    public ResponseEntity<Page<LoanResponse>> getAuthenticatedUserLoansWithPage(HttpServletRequest request,
                                                          @RequestParam(required = false, value = "page", defaultValue = "0") int page,
                                                          @RequestParam(required = false,value = "size", defaultValue = "20") int size,
                                                          @RequestParam(required = false,value = "sort", defaultValue = "loanDate") String prop,
                                                          @RequestParam(required = false,value = "type", defaultValue = "DESC") Sort.Direction type){

        Pageable pageable = PageRequest.of(page,size,Sort.by(type,prop));
        Long idLogin = (Long) request.getAttribute("id");
        Page<LoanResponse> loanAutUser = loanService.getAuthenticatedUserLoans(pageable,idLogin);

        return ResponseEntity.ok(loanAutUser);
    }

    //It will return details of a loan of authenticated user
    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/{id}")
    public ResponseEntity<LoanResponse> getAuthenticatedUserLoanWithId(HttpServletRequest request, @PathVariable  Long id){

        Long idLogin = (Long) request.getAttribute("id");
        LoanResponse loanAutUser = loanService.getAuthenticatedUserLoanWithId(idLogin,id);

        return ResponseEntity.ok(loanAutUser);
    }

    //It will return loans of specified user by id
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @GetMapping("/user/{id}")
    public ResponseEntity<Page<LoanResponse>> getLoansSpecifiedUserById( @PathVariable  Long id,
                                                               @RequestParam(required = false, value = "page", defaultValue = "0") int page,
                                                               @RequestParam(required = false,value = "size", defaultValue = "20") int size,
                                                               @RequestParam(required = false,value = "sort", defaultValue = "loanDate") String prop,
                                                               @RequestParam(required = false,value = "type", defaultValue = "DESC") Sort.Direction type){

        Pageable pageable = PageRequest.of(page,size,Sort.by(type,prop));
        Page<LoanResponse> loanSpecifiedUser = loanService.getLoansSpecifiedUserById(pageable,id);

        return ResponseEntity.ok(loanSpecifiedUser);
    }

    //It will return loans of specified book by id
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @GetMapping("/book/{id}")
    public ResponseEntity<Page<LoanResponseBook>> getLoansSpecifiedBookById(@PathVariable  Long id,
                                                               @RequestParam(required = false, value = "page", defaultValue = "0") int page,
                                                               @RequestParam(required = false,value = "size", defaultValue = "20") int size,
                                                               @RequestParam(required = false,value = "sort", defaultValue = "loanDate") String prop,
                                                               @RequestParam(required = false,value = "type", defaultValue = "DESC") Sort.Direction type){

        Pageable pageable = PageRequest.of(page,size,Sort.by(type,prop));
        Page<LoanResponseBook> loanSpecifiedUser = loanService.getLoansSpecifiedBookById(pageable,id);

        return ResponseEntity.ok(loanSpecifiedUser);
    }

    //It will return details of a loan of any user
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @GetMapping("/auth/{id}")
    public ResponseEntity<LoanResponseBookUser> getAuthenticatedUserLoanId(@PathVariable  Long id){

        LoanResponseBookUser loanAutUser = loanService.getloanBookAndUser(id);

        return ResponseEntity.ok(loanAutUser);
    }


    //It will update the loan
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @PutMapping("/{id}")
    public ResponseEntity<LoanUpdateResponse> updateLoan(@PathVariable Long id, @Valid @RequestBody LoanUpdateRequest loanUpdateRequest){

        LoanUpdateResponse loanUpdate = loanService.updateLoan(id,loanUpdateRequest);

        return ResponseEntity.ok(loanUpdate);
    }
}
