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

package org.javacs.navigation;

import com.sun.source.tree.*;
import com.sun.source.util.*;
import java.util.List;
import javax.lang.model.element.Element;

class FindReferences extends TreePathScanner<Void, List<TreePath>> {
    final JavacTask task;
    final Element find;

    FindReferences(JavacTask task, Element find) {
        this.task = task;
        this.find = find;
    }

    @Override
    public Void visitIdentifier(IdentifierTree t, List<TreePath> list) {
        if (check()) {
            list.add(getCurrentPath());
        }
        return super.visitIdentifier(t, list);
    }

    @Override
    public Void visitMemberSelect(MemberSelectTree t, List<TreePath> list) {
        if (check()) {
            list.add(getCurrentPath());
        }
        return super.visitMemberSelect(t, list);
    }

    @Override
    public Void visitNewClass(NewClassTree t, List<TreePath> list) {
        if (check()) {
            list.add(getCurrentPath());
        }
        return super.visitNewClass(t, list);
    }

    @Override
    public Void visitMemberReference(MemberReferenceTree t, List<TreePath> list) {
        if (check()) {
            list.add(getCurrentPath());
        }
        return super.visitMemberReference(t, list);
    }

    private boolean check() {
        var candidate = Trees.instance(task).getElement(getCurrentPath());
        return find.equals(candidate);
    }
}
