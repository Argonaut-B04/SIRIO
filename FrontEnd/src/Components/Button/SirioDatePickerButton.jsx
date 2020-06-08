import React from "react";
import DatePicker from "react-datepicker";
import SirioDatePickerCustomInput from "./SirioDatePickerCustomInput";
import classes from "./SirioDatePickerButton.module.css";
import { enGB } from 'date-fns/locale'
import "react-datepicker/dist/react-datepicker.css";

export default class SirioDatePickerButton extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            selectedDate: null,
            idTarget: null
        };

        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(date) {
        this.setState({
            selectedDate: date
        });
    };

    render() {
        const { id, minDate, popper, maxDate, selectedDate } = this.props;
        const { panel } = classes;
        return (
            <DatePicker
                selected={selectedDate}
                onChange={date => {
                    this.handleChange(date);
                    this.props.handleChange(date, id);
                }}
                popperClassName={panel}
                disabledKeyboardNavigation
                locale={enGB}
                popperPlacement={popper || "bottom-end"}
                dateFormat="yyyy-MM-dd"
                minDate={minDate ? minDate : new Date()}
                maxDate={maxDate}
                filterDate={this.props.filter}
                dropdownMode="select"
                customInput={<SirioDatePickerCustomInput {...this.props} />}
            />
        );
    }
}