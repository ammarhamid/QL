package org.uva.sea.ql.ast.expression.impl;

import org.uva.sea.ql.Message;
import org.uva.sea.ql.ast.expression.ExprNode;
import org.uva.sea.ql.ast.expression.UnaryNode;
import org.uva.sea.ql.type.Type;
import org.uva.sea.ql.type.impl.BooleanType;
import org.uva.sea.ql.value.Value;
import org.uva.sea.ql.visitor.ExpressionVisitor;

import java.util.Collection;
import java.util.Map;

public class NotNode extends UnaryNode
{

    public NotNode(final ExprNode exprNode)
    {
        super(exprNode);
    }

    @Override
    public <T> T accept(ExpressionVisitor<T> expressionVisitor)
    {
        return expressionVisitor.visit(this);
    }

    @Override
    public Value evaluate(final Map<IdentifierNode, Value> variables)
    {
        final Value value = this.exprNode.evaluate(variables);
        return value.not();
    }

    @Override
    public Type getType()
    {
        return new BooleanType();
    }

    @Override
    public boolean validate(final Collection<Message> errors)
    {
        final Type type = this.exprNode.getType();
        final boolean compatible = type.isCompatibleToBoolean();

        if(!compatible)
        {
            errors.add(createErrorMessage());
        }

        return compatible;
    }

    @Override
    protected String getOperator()
    {
        return "!";
    }
}
