package com.orlovsky.mooc_platform.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class TestStepOptionRequestDTO {
    private UUID id;
    private String optionText;
    private boolean isCorrect;

    public UUID getId() {
        return this.id;
    }

    public String getOptionText() {
        return this.optionText;
    }

    public boolean isCorrect() {
        return this.isCorrect;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public void setCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof TestStepOptionRequestDTO)) return false;
        final TestStepOptionRequestDTO other = (TestStepOptionRequestDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$optionText = this.getOptionText();
        final Object other$optionText = other.getOptionText();
        if (this$optionText == null ? other$optionText != null : !this$optionText.equals(other$optionText))
            return false;
        if (this.isCorrect() != other.isCorrect()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TestStepOptionRequestDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $optionText = this.getOptionText();
        result = result * PRIME + ($optionText == null ? 43 : $optionText.hashCode());
        result = result * PRIME + (this.isCorrect() ? 79 : 97);
        return result;
    }

    public String toString() {
        return "TestStepOptionRequestDTO(id=" + this.getId() + ", optionText=" + this.getOptionText() + ", isCorrect=" + this.isCorrect() + ")";
    }
}
