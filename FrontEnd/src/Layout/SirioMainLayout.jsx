import React, { Component } from 'react';
import classes from './SirioMainLayout.module.css';
import SirioSideNav from "../Components/SideNav/SirioSideNav";
import { Ripple } from "react-preloaders";
import LoadingOverlay from 'react-loading-overlay';
import SirioButton from '../Components/Button/SirioButton';

/**
 * Kelas yang akan digunakan untuk menampilkan layout utama halaman Sirio
 */
export default class SirioMainLayout extends Component {

    constructor(props) {
        super(props);

        this.state = {
            showing: window.innerWidth > 1200
        }

        this.toggleNav = this.toggleNav.bind(this)
        this.updateWindowDimensions = this.updateWindowDimensions.bind(this);
    }

    toggleNav() {
        const prev = this.state.showing;
        this.setState({
            showing: !prev
        })
    }

    updateWindowDimensions() {
        this.setState({
            showing: window.innerWidth > 1200
        })
    }
    // INFO: Seluruh Props harus disediakan dari komponen "pages"

    render() {
        window.addEventListener('resize', this.updateWindowDimensions);

        // Jika props preloader tersedia, maka komponen akan menggunakan preloader
        // props preloader berisi boolean yang dapat diubah ubah
        var preloader;
        if (typeof this.props.preloader !== 'undefined') {
            preloader = <Ripple time={0} customLoading={this.props.preloader ? this.props.preloader : false} color={"white"} background={"linear-gradient(180deg, rgba(2,0,36,1) 0%, rgba(127,63,152,1) 100%);"} />
        }

        // Untuk menampilkan, navigasi apa yang harus ditampilkan
        var targetClass = classes.mainContent;
        var topbar = classes.topbar;
        if (this.state.showing) {
            targetClass = [targetClass, classes.show].join(" ");
            topbar = [topbar, classes.hiddenBar].join(" ");
        } else if (this.props.disableSideNav) {
            targetClass = [targetClass, classes.fullWidth].join(" ");
        } else if (this.props.transparent) {
            targetClass = classes.mainContentTransparent;
        }

        return (
            <>
                {preloader}
                <SirioSideNav disableSideNav={this.props.disableSideNav} shouldShow={this.state.showing} toggleNav={this.toggleNav} />

                {/* Jika props contentLoading tersedia, maka komponen akan menggunakan loader content */}
                <LoadingOverlay
                    active={this.props.contentLoading ? this.props.contentLoading : false}
                    spinner
                    // Isi dari loader content dapat diubah melalui props loadingBody
                    text={this.props.loadingBody ? this.props.loadingBody : 'Loading content...'}
                    className={targetClass}
                >

                    <div className={topbar}>
                        <SirioButton
                            blue
                            square
                            hover
                            insideClass={classes.burgerButton}
                            onClick={() => { this.toggleNav() }}>
                            ≡
                        </SirioButton>
                        <img src={process.env.PUBLIC_URL + '/fullLogoLight.png'} alt="SIRIO Logo" height="30px" />
                    </div>
                    <div className={classes.pageContent}>
                        {this.props.children}
                    </div>
                </LoadingOverlay>
            </>
        );
    }
}