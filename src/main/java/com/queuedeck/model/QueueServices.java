/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuedeck.model;

/**
 *
 * @author ABDULRAHMAN ILLO
 */
public class QueueServices {
    
    private String q_no;
    private String service;

    public QueueServices(String q_no, String service) {
        this.q_no = q_no;
        this.service = service;
    }

    public QueueServices() {
    }

    public String getQ_no() {
        return q_no;
    }

    public void setQ_no(String q_no) {
        this.q_no = q_no;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
    
    
    
}
