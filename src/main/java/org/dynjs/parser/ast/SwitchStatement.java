/**
 *  Copyright 2012 Douglas Campos, and individual contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.dynjs.parser.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dynjs.parser.CodeVisitor;
import org.dynjs.parser.js.Position;
import org.dynjs.runtime.ExecutionContext;

public class SwitchStatement extends BaseStatement {

    private Expression expr;
    private List<CaseClause> caseClauses;
    private DefaultCaseClause defaultClause;

    public SwitchStatement(Position position, Expression expr, List<CaseClause> caseClauses) {
        super(position);
        this.expr = expr;
        this.caseClauses = caseClauses;
        Iterator<CaseClause> caseIter = caseClauses.iterator();

        while (caseIter.hasNext()) {
            CaseClause each = caseIter.next();
            if (each instanceof DefaultCaseClause) {
                this.defaultClause = (DefaultCaseClause) each;
                caseIter.remove();
            }
        }
    }

    public Expression getExpr() {
        return this.expr;
    }

    public List<CaseClause> getCaseClauses() {
        return this.caseClauses;
    }

    public DefaultCaseClause getDefaultCaseClause() {
        return this.defaultClause;
    }

    public List<VariableDeclaration> getVariableDeclarations() {
        List<VariableDeclaration> decls = new ArrayList<>();
        for (CaseClause each : caseClauses) {
            decls.addAll(each.getVariableDeclarations());
        }
        if (this.defaultClause != null) {
            decls.addAll(this.defaultClause.getVariableDeclarations());
        }
        return decls;
    }

    public String toIndentedString(String indent) {
        StringBuffer buf = new StringBuffer();

        buf.append(indent).append("switch (").append(expr.toString()).append(" ) {\n");
        for (CaseClause each : caseClauses) {
            buf.append(each.toIndentedString("  " + indent));
        }
        buf.append(indent).append("}");

        return buf.toString();
    }

    @Override
    public void accept(ExecutionContext context, CodeVisitor visitor, boolean strict) {
        visitor.visit(context, this, strict);
    }

}
