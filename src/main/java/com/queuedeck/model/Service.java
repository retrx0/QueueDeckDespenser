/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuedeck.model;

import com.queuedeck.controller.DispenserFXMLController;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ABDULRAHMAN ILLO
 */
public class Service {

    String serviceNo;
    String serviceName;
    Boolean locked;
    String lockedByStaff;
    String unlockTime;

    List<Service> services = new ArrayList<>();

    public Service() {
        
    }

    public Service(String serviceNo, String serviceName, Boolean locked, String lockedByStaff, String unlockTime) {
        this.serviceNo = serviceNo;
        this.serviceName = serviceName;
        this.locked = locked;
        this.lockedByStaff = lockedByStaff;
        this.unlockTime = unlockTime;
    }

    public List<Service> listServices(){
        List<Service> l = new ArrayList<>();
        try {
            Connection con = DispenserFXMLController.pool.getConnection();
            ResultSet rs = con.prepareStatement("select s_no, service, staff_no, locked, unlock_time from services").executeQuery();
            while (rs.next()) {
                Service s = new Service(rs.getString("s_no"), rs.getString("service"), rs.getBoolean("locked"), rs.getString("staff_no"), rs.getString("unlock_time"));
                l.add(s);
            }
            DispenserFXMLController.pool.releaseConnection(con);
        } catch (SQLException s) {}
        
        return l;
    }
    
    public void unlockService(String sno){
        try {
            Connection con = DispenserFXMLController.pool.getConnection();
            con.prepareStatement("update services set locked = '0', unlock_time = null where s_no = '"+sno+"'").executeUpdate();
            DispenserFXMLController.pool.releaseConnection(con);
        } catch (SQLException s) {}
    }
    
    public String getServiceName() {
        return serviceName;
    }

    public String getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(String serviceNo) {
        this.serviceNo = serviceNo;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getLockedByStaff() {
        return lockedByStaff;
    }

    public void setLockedByStaff(String lockedByStaff) {
        this.lockedByStaff = lockedByStaff;
    }

    public String getUnlockTime() {
        return unlockTime;
    }

    public void setUnlockTime(String unlockTime) {
        this.unlockTime = unlockTime;
    }

    @Override
    public String toString() {
        return "Service{" + "serviceNo=" + serviceNo + ", serviceName=" + serviceName + ", locked=" + locked + ", lockedByStaff=" + lockedByStaff + ", unlockTime=" + unlockTime + '}'+'\n';
    }

}
