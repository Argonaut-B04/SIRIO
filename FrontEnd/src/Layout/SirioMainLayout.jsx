import React, { Component } from 'react';
import classes from './SirioMainLayout.module.css';
import SirioSideNav from "../Components/SideNav/SirioSideNav";
import { Ripple } from "react-preloaders";
import LoadingOverlay from 'react-loading-overlay';

/**
 * Kelas yang akan digunakan untuk menampilkan layout utama halaman Sirio
 */
export default class SirioMainLayout extends Component {

    // INFO: Seluruh Props harus disediakan dari komponen "pages"

    render() {
        // Jika props preloader tersedia, maka komponen akan menggunakan preloader
        // props preloader berisi boolean yang dapat diubah ubah
        var preloader;
        if (typeof this.props.preloader !== 'undefined') {
            preloader = <Ripple time={0} customLoading={this.props.preloader ? this.props.preloader : false} color={"white"} background={"linear-gradient(180deg, rgba(2,0,36,1) 0%, rgba(127,63,152,1) 100%);"} />
        }

        var targetClass = classes.mainContent;
        if (this.props.disableSideNav) {
            targetClass = [targetClass, classes.fullWidth].join(" ");
        } else if (this.props.transparent) {
            targetClass = classes.mainContentTransparent;
        }
        return (
            <>
                {preloader}
                <SirioSideNav disableSideNav={this.props.disableSideNav} />

                {/* Jika props contentLoading tersedia, maka komponen akan menggunakan loader content */}
                <LoadingOverlay
                    active={this.props.contentLoading ? this.props.contentLoading : false}
                    spinner
                    // Isi dari loader content dapat diubah melalui props loadingBody
                    text={this.props.loadingBody ? this.props.loadingBody : 'Loading content...'}
                    className={targetClass}
                >
                    <div className={classes.pageContent}>
                        {this.props.children}
                    </div>
                </LoadingOverlay>
            </>
        );
    }
}