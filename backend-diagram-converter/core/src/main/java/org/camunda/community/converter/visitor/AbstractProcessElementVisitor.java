package org.camunda.community.converter.visitor;

import org.camunda.community.converter.BpmnDiagramCheckResult.Severity;
import org.camunda.community.converter.DomElementVisitorContext;
import org.camunda.community.converter.convertible.Convertible;
import org.camunda.community.converter.message.MessageFactory;

public abstract class AbstractProcessElementVisitor extends AbstractElementVisitor {
  @Override
  protected String namespaceUri(DomElementVisitorContext context) {
    return context.getProperties().getBpmnNamespace().getUri();
  }

  @Override
  protected final void visitFilteredElement(DomElementVisitorContext context) {
    context.setAsBpmnProcessElement(createConvertible(context));
    postCreationVisitor(context);
    if (!canBeConverted(context)) {
      context.addMessage(
          Severity.WARNING,
          MessageFactory.elementNotSupportedHint(context.getElement().getLocalName()));
    }
  }

  public abstract boolean canBeConverted(DomElementVisitorContext context);

  protected abstract Convertible createConvertible(DomElementVisitorContext context);

  protected void postCreationVisitor(DomElementVisitorContext context) {
    // do nothing
  }
}
