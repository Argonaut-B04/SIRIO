import React, { Component } from 'react';
import SirioField from './SirioFormComponent/SirioField';
import SirioComponentHeader from '../Header/SirioComponentHeader';
import ComponentWrapper from '../../Layout/ComponentWrapper';

/**
 * Komponen form untuk Sirio secara umum
 * 
 * Props yang tersedia:
 * - title              : String, Judul Form
 * - headerButton       : Komponen, yang akan ditampilkan di sisi sebelah kanan title
 * - inputDefinition    : List of JSON, definisi dari tiap field. See also SirioField.
 * - onSubmit           : Function, fungsi yang akan dijalankan ketika user submit form. 
 * - submitButton       : Komponen, button submit. Jika tidak disediakan, akan menggunakan tombol default
 */
class SirioForm extends Component {

    render() {
        const { noHeader, customHeader, title, subtitle, betweenTitleSubtitle, inputDefinition, isInnerForm, childForm, footerButton, onSubmit, submitButton } = this.props;
        
        var header;
        if (noHeader || customHeader) {
            header = customHeader
        } else {
            header = <SirioComponentHeader
                title={title}
                subtitle={subtitle}
                betweenTitleSubtitle={betweenTitleSubtitle}
            />
        }

        var fields =
            <>
                {inputDefinition.map((field, i) =>
                    <SirioField
                        key={i}
                        label={field.label}
                        handleChange={field.handleChange}
                        index={field.index}
                        type={field.type}
                        name={field.name}
                        value={field.value}
                        placeholder={field.placeholder}
                        optionList={field.optionList}
                        classes={field.classes}
                        customInput={field.customInput}
                        fullComponent={field.fullComponent}
                        validator={field.validation}
                        required={field.required}
                        min={field.min}
                        max={field.max}
                        multiple={field.multiple}
                        sideButton={field.sideButton}
                        modifier={field.modifier}
                        validationFunction={field.validationFunction}
                        disabled={field.disabled}
                    />
                )}
            </>

        if (isInnerForm || childForm) {
            return (
                <>
                    {header}
                    <ComponentWrapper classes="m-4">
                        {fields}

                        {footerButton &&
                            <div className="w-100 text-right">
                                <br />
                                {footerButton}
                            </div>
                        }
                    </ComponentWrapper>
                </>
            )
        } else {
            return (
                <>
                    {header}
                    <ComponentWrapper>
                        <form onSubmit={onSubmit}>
                            {fields}
                            <div className="w-100 text-right mt-5">
                                {submitButton}
                            </div>
                        </form>
                    </ComponentWrapper>
                </>
            )
        }

    }
}
export default SirioForm;