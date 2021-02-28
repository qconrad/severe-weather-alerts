package com.severeweatheralerts.Adapters;

import static com.severeweatheralerts.TextUtils.TextBeautifier.beautify;

public class InstructionAdapter {
  private final String instruction;
  private final String type;

  public InstructionAdapter(String instruction, String type) {
    this.instruction = instruction;
    this.type = type;
  }

  public String adaptInstruction() {
    if (isNull() || isCancellation()) return null;
    return beautify(instruction);
  }

  private boolean isNull() {
    return instruction == null ||
           instruction.equals("");
  }

  private boolean isCancellation() {
    return type != null && type.equals("Cancel");
  }
}
