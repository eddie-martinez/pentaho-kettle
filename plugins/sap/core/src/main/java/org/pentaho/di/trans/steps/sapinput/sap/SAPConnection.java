/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.di.trans.steps.sapinput.sap;

import java.util.Collection;

import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.trans.steps.sapinput.sap.impl.SAPRowIterator;

/* Copyright (c) 2010 Aschauer EDV GmbH.  All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This software was developed by Aschauer EDV GmbH and is provided under the terms
 * of the GNU Lesser General Public License, Version 2.1. You may not use
 * this file except in compliance with the license. If you need a copy of the license,
 * please go to http://www.gnu.org/licenses/lgpl-2.1.txt.
 *
 * Software distributed under the GNU Lesser Public License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to
 * the license for the specific language governing your rights and limitations.
 *
 * Please contact Aschauer EDV GmbH www.aschauer-edv.at if you need additional
 * information or have any questions.
 *
 * @author  Robert Wintner robert.wintner@aschauer-edv.at
 * @since   PDI 4.0
 */

public interface SAPConnection {

  /**
   * Open a connection to SAP ERP Note: method for init()
   *
   *
   * @param sapConnection
   *          The SAP Connection to use, needs to be of type SAP ERP
   * @throws SAPException
   *           in case something went wrong during the connection phase.
   */
  void open( DatabaseMeta sapConnection ) throws SAPException;

  void open( SAPConnectionParams params ) throws SAPException;

  /**
   * Close the connection
   */
  void close();

  // ///////////////////
  // methods for UI
  // ///////////////////

  Collection<SAPFunction> getFunctions( String query ) throws SAPException;

  SAPFunction getFunction( String name ) throws SAPException;

  SAPFunctionSignature getFunctionSignature( SAPFunction function ) throws SAPException;

  // ///////////////////
  // methods for data
  // ///////////////////

  // use this for possibly large amounts of data
  SAPRowIterator executeFunctionCursored( SAPFunction function, Collection<SAPField> input,
    Collection<SAPField> output ) throws SAPException;

  // use this for small amounts of data (e.g. for testcases)
  SAPResultSet executeFunctionUncursored( SAPFunction function, Collection<SAPField> input,
    Collection<SAPField> output ) throws SAPException;
}
