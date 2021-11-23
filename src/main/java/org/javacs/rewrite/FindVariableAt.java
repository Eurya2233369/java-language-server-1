/************************************************************************************
 * This file is part of Java Language Server (https://github.com/itsaky/java-language-server)
 *
 * Copyright (C) 2021 Akash Yadav
 *
 * Java Language Server is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Java Language Server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Java Language Server.  If not, see <https://www.gnu.org/licenses/>.
 *
**************************************************************************************/

package org.javacs.rewrite;

import com.sun.source.tree.*;
import com.sun.source.util.*;

class FindVariableAt extends TreeScanner<VariableTree, Integer> {
    private final SourcePositions pos;
    private CompilationUnitTree root;

    FindVariableAt(JavacTask task) {
        pos = Trees.instance(task).getSourcePositions();
    }

    @Override
    public VariableTree visitCompilationUnit(CompilationUnitTree t, Integer find) {
        root = t;
        return super.visitCompilationUnit(t, find);
    }

    @Override
    public VariableTree visitVariable(VariableTree t, Integer find) {
        var smaller = super.visitVariable(t, find);
        if (smaller != null) {
            return smaller;
        }
        if (pos.getStartPosition(root, t) <= find && find < pos.getEndPosition(root, t)) {
            return t;
        }
        return null;
    }

    @Override
    public VariableTree reduce(VariableTree r1, VariableTree r2) {
        if (r1 != null) return r1;
        return r2;
    }
}
