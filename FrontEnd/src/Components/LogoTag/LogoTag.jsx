import React from 'react';
import classes from './LogoTag.module.css';

/**
 * Komponen Logo dan Tag dari Sirio
 * 
 * Props yang tersedia:
 * - light              : boolean, mengubah warna teks tag gelap / terang
 */
export default class LogoTag extends React.Component {
    render() {
        const { logo, logoImage, logoName, logoNameDark, logoDesc, back } = classes;
        const { light, includeBack, className, noDesc } = this.props;
        return (
            <>
                <div className={[logo, className].join(" ")}>
                    <img src={process.env.PUBLIC_URL + '/logo.png'} className={logoImage} alt="SIRIO Logo" />
                    <h1 className={light ? [logoNameDark, logoName].join(" ") : logoNameDark}>SIRIO</h1>
                    {includeBack && <img src={process.env.PUBLIC_URL + '/backLogo.png'} className={back} alt="Back Logo" onClick={() => window.location.href = "/"} />}
                </div>
                {!noDesc && <h4 className={logoDesc}>
                    Sistem Informasi Risiko Operasional
                </h4>
                }
            </>
        );
    }
}