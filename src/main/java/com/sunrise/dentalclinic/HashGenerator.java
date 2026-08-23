package com.sunrise.dentalclinic;

import com.sunrise.dentalclinic.service.AuthService;

/**
 * TEMPORARY utility — run this once to generate correct password hashes,
 * then paste the output into an UPDATE statement in phpMyAdmin.
 * You can delete this class afterward, it's not part of the real app.
 */
public class HashGenerator {
    public static void main(String[] args) {
        System.out.println("admin123 -> " + AuthService.hash("admin123"));
        System.out.println("reception123 -> " + AuthService.hash("reception123"));
    }
}