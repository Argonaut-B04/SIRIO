import React, { Component } from 'react';
import SirioButton from "../Button/SirioButton";
import SirioAxiosBase from '../../Services/SirioAxiosBase';

class SirioDatePickerCustomInput extends Component {

    render() {
        const { value, unchangedContent, onClick } = this.props;
        var content;
        if (unchangedContent) {
            content = this.props.children;
        } else {
            content = value ? SirioAxiosBase.formatDateFromSirioDatePicker(value) : this.props.children;
        }

        return (
            <SirioButton
                onClick={onClick}
                {...this.props}
            >
                {content}
            </SirioButton>
        );
    }
}

export default SirioDatePickerCustomInput;