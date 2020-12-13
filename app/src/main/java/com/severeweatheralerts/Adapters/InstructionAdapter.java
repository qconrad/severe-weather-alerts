package com.severeweatheralerts.Adapters;

import static com.severeweatheralerts.TextUtils.TextBeautifier.beautify;

public class InstructionAdapter {
  private String instruction;
  private String type;

  public InstructionAdapter(String instruction, String type) {
    this.instruction = instruction;
    this.type = type;
  }

  public String adaptInstruction() {
    if (isCancellation()) return null; // Ignore instructions for cancels
    return beautify(instruction);
  }

  private boolean isCancellation() {
    return type != null && type.equals("Cancel");
  }
}
