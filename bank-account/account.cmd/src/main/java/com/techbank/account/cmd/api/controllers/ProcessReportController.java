package com.techbank.account.cmd.api.controllers;

import com.techbank.account.cmd.api.commands.ProcessReportCommand;
import com.techbank.account.cmd.api.dto.ProcessReportResponse;
import com.techbank.account.common.dto.BaseResponse;
import com.techbank.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/processReport")
public class ProcessReportController {
    private final Logger logger = Logger.getLogger(OpenAccountController.class.getName());

    @Autowired
    private CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<BaseResponse> processReport(@RequestBody ProcessReportCommand command) {
        var id = UUID.randomUUID().toString();
        try {
            command.setId(id);
            commandDispatcher.send(command);
            return new ResponseEntity<>(new BaseResponse("Report processed successfully"), HttpStatus.OK);
        } catch (IllegalStateException e) {
            logger.log(Level.WARNING, MessageFormat.format("Client made a bad request - {0}.", e.toString()));
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeErrorMessage = MessageFormat.format("Error while processing request for weekly " +
                    "report for account - {0}", command.getAccountName());
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new ProcessReportResponse(safeErrorMessage, id, command.getAccountName())
                    , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
