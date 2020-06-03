//
// File:        Profile.java
// Package:     gov.llnl.babel.config
// Revision:    @(#) $Id: Profile.java 7188 2011-09-27 18:38:42Z adrian $
// Description: a single machine configuration profile for the Babel compiler
//
// Copyright (c) 2000-2001, Lawrence Livermore National Security, LLC
// Produced at the Lawrence Livermore National Laboratory.
// Written by the Components Team <components@llnl.gov>
// UCRL-CODE-2002-054
// All rights reserved.
// 
// This file is part of Babel. For more information, see
// http://www.llnl.gov/CASC/components/. Please read the COPYRIGHT file
// for Our Notice and the LICENSE file for the GNU Lesser General Public
// License.
// 
// This program is free software; you can redistribute it and/or modify it
// under the terms of the GNU Lesser General Public License (as published by
// the Free Software Foundation) version 2.1 dated February 1999.
// 
// This program is distributed in the hope that it will be useful, but
// WITHOUT ANY WARRANTY; without even the IMPLIED WARRANTY OF
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the terms and
// conditions of the GNU Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public License
// along with this program; if not, write to the Free Software Foundation,
// Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

package gov.llnl.babel.config;

import java.util.Map;

/**
 * Class <code>Profile</code> defines one particular machine configuration
 * profile for the Babel compiler.  A profile consists of the following items:
 * <ul>
 * <li>a profile name</li>
 * <li>the programming language name</li>
 * <li>a brief summary of the profile</li>
 * <li>a longer HTML description of the profile</li>
 * <li>a CPU machine hardware description (e.g., sparc)</li>
 * <li>an OS operating system description (e.g., SunOS)</li>
 * <li>an OS release number (e.g., 5.7)</li>
 * <li>a compiler name (e.g., KCC)</li>
 * <li>a compiler version (e.g., 4.3d)</li>
 * <li>the configuration information</li>
 * </ul>
 * The configuration information is stored as (keyword,value) pairs
 * of strings in a <code>Map</code>.  Any of the above values except
 * the profile name and programming language name may be null if its
 * value was not provided from the configuration input file.
 */
public class Profile {
   private String d_profile_name;  // unique name of this profile
   private String d_language;      // profile is for this language
   private String d_summary;       // brief summary of the profile
   private String d_description;   // HTML description of the profile
   private String d_cpu;           // CPU name
   private String d_os;            // operating system name
   private String d_release;       // release of the operating system
   private String d_compiler;      // name of the compiler
   private String d_version;       // compiler version
   private Map    d_configuration; // (keyword,value) pairs

   /**
    * The <code>Profile</code> constructor takes a profile name and a
    * language as string arguments.  These two arguments should not be
    * null.
    */
   public Profile(String profile_name, String language) {
      d_profile_name  = profile_name;
      d_language      = language;
      d_summary       = null;
      d_description   = null;
      d_cpu           = null;
      d_os            = null;
      d_release       = null;
      d_compiler      = null;
      d_version       = null;
      d_configuration = null;
   }
      
   /**
    * Return the name of the profile as a string.
    */
   public String getProfileName() {
      return d_profile_name;
   }

   /**
    * Return the name of the language for which this profile was
    * defined.
    */
   public String getLanguage() {
      return d_language;
   }

   /**
    * Get a brief summary of the profile.  This summary may be null if
    * it was not provided in the configuration input file.
    */
   public String getSummary() {
      return d_summary;
   }

   /**
    * Set the brief summary for the profile.
    */
   public void setSummary(String summary) {
      d_summary = summary;
   }

   /**
    * Get a long HTML description of the profile.  This description may
    * be null if it was not provided in the configuration input file.
    */
   public String getDescription() {
      return d_description;
   }

   /**
    * Set the description for the profile.  This string is intended to
    * be a long HTML description of the purpose of the profile.
    */
   public void setDescription(String description) {
      d_description = description;
   }

   /**
    * Get the CPU description for which the profile is valid.
    * This description may be null if it was not provided in the
    * configuration input file.
    */
   public String getCPU() {
      return d_cpu;
   }

   /**
    * Set the CPU description for the profile.
    */
   public void setCPU(String cpu) {
      d_cpu = cpu;
   }

   /**
    * Get the operating system description for which the profile is
    * valid.  This return value may be null if an OS description was
    * not provided in the configuration input file.
    */
   public String getOS() {
      return d_os;
   }

   /**
    * Set the operating system description for this profile.
    */
   public void setOS(String os) {
      d_os = os;
   }

   /**
    * Get the operating system release version for which this profile
    * is valid.  This return value may be null if an OS version was
    * not provided in the configuration input file.
    */
   public String getOSRelease() {
      return d_release;
   }

   /**
    * Set the operating system release for this profile.
    */
   public void setOSRelease(String release) {
      d_release = release;
   }

   /**
    * Get the compiler name for which this profile is valid.  This
    * return value may be null if a compiler name was not provided in
    * the configuration input file.
    */
   public String getCompilerName() {
      return d_compiler;
   }

   /**
    * Set the compiler name for this profile.
    */
   public void setCompilerName(String compiler) {
      d_compiler = compiler;
   }

   /**
    * Get the compiler version number for which this profile is
    * valild.  This return value may be null if a compiler version
    * number was not provided in the configuration input file.
    */
   public String getCompilerVersion() {
      return d_version;
   }

   /**
    * Set the compiler version for this profile.
    */
   public void setCompilerVersion(String version) {
      d_version = version;
   }

   /**
    * Get the configuration information for this profile.  The
    * configuration information is stored as a <code>Map</code> of
    * keyword-value pairs of strings that describe the profile options
    * and associated selections for each option.  This return may be
    * null if there are no valid options for this particular profile.
    */
   public Map getConfiguration() {
      return d_configuration;
   }

   /**
    * Set the configuration information for this profile.
    */
   public void setConfiguration(Map configuration) {
      d_configuration = configuration;
   }
}
