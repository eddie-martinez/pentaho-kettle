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


package org.pentaho.di.core.database;

import org.junit.Assert;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith( Parameterized.class )
public class SqlCommentScrubberTest {

  private String input;
  private String expected;

  public SqlCommentScrubberTest( String input, String expected ) {
    this.input = input;
    this.expected = expected;
  }

  @Parameters
  public static Collection<String[]> sqlQueries() {
    return Arrays.asList(
      new String[][] {
        { null, null },
        { "", "" },
        { "SELECT col1 FROM test", "SELECT col1 FROM test" },
        { "SELECT col1 FROM test --end comment", "SELECT col1 FROM test " },
        { "SELECT \n col1, col2\n FROM \n test", "SELECT \n col1, col2\n FROM \n test" },
        { "SELECT \n \"col1\", col2\n FROM --test\n test", "SELECT \n \"col1\", col2\n FROM  test" },
        { "SELECT /* \"my_column'\" */ col1 FROM /* 'my_table' */ account", "SELECT  col1 FROM  account" },
        { "SELECT '/' as col1, '*/*' as regex ", "SELECT '/' as col1, '*/*' as regex " },
        { "SELECT INSTR('/loader/*/*.txt', '/') - INSTR('/loader/*/*.txt', '/') ",
          "SELECT INSTR('/loader/*/*.txt', '/') - INSTR('/loader/*/*.txt', '/') " },
        { "SELECT /* my data*/ col1, col2, col3 FROM account WHERE name = 'Pentaho'",
          "SELECT  col1, col2, col3 FROM account WHERE name = 'Pentaho'" }
      } );
  }

  @Test
  public void testRemoveComments() throws Exception {
    Assert.assertEquals( expected, SqlCommentScrubber.removeComments( input ) );
  }
}
